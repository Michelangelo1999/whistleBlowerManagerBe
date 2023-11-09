package com.whistleblowermanagerbe.service;

import com.whistleblowermanagerbe.Enum.StatoSegnalazione;
import com.whistleblowermanagerbe.dto.SegnalazioneDto;
import com.whistleblowermanagerbe.dto.SoggettoCoinvoltoDto;
import com.whistleblowermanagerbe.model.*;
import com.whistleblowermanagerbe.repo.AllegatoRepository;
import com.whistleblowermanagerbe.repo.DatiUtenteRepository;
import com.whistleblowermanagerbe.repo.InfoSegnalazioneRepository;
import com.whistleblowermanagerbe.repo.SegnalazioneRepository;
import com.whistleblowermanagerbe.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SegnalazioneService {

    private static final String CHARACTERS = "0123456789";
    private static final int KEY_LENGTH = 16;

    @Autowired
    private SegnalazioneRepository segnalazioneRepository;

    @Autowired
    private AllegatoRepository allegatoRepository;

    @Autowired
    private DatiUtenteRepository datiUtenteRepository;

    @Autowired
    private InfoSegnalazioneRepository infoSegnalazioneRepository;

    private String generateKey16(){
        SecureRandom random = new SecureRandom();
        StringBuilder keyBuilder = new StringBuilder(KEY_LENGTH);

        for (int i = 0; i < KEY_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            keyBuilder.append(randomChar);
        }

        return keyBuilder.toString();
    }

    public String addSegnalazione(SegnalazioneDto dto){
        Segnalazione s = segnalazioneRepository.save(convert(dto));
        addInfoSegnalazione(s);
        return s.getKey16();
    }

    private void addInfoSegnalazione(Segnalazione s){
        InfoSegnalazione infoSegnalazione = new InfoSegnalazione();
        infoSegnalazione.setDataCreazione(LocalDate.now());
        infoSegnalazione.setUltimoAggiornamento(LocalDate.now());
        infoSegnalazione.setSegnalazione(s);
        infoSegnalazione.setIdentitaFornita(!s.getIdentita().getIsAnonimo());
        infoSegnalazione.setIdentitaVerificata(false);
        infoSegnalazione.setStato(StatoSegnalazione.NON_ANCORA_PRESA_IN_CARICO.name());
        infoSegnalazione.setNovantesimi(0);
        infoSegnalazioneRepository.save(infoSegnalazione);
    }

    private Segnalazione convert(SegnalazioneDto dto){
        Segnalazione s = new Segnalazione();

        s.setKey16(generateKey16());
        s.setRuoloSegnalante(dto.getRuoloSegnalante());
        s.setDenominazioneCompletaUfficio(dto.getDenominazioneCompletaUfficio());
        s.setRagioneSociale(dto.getRagioneSociale());
        s.setIndirizzoSede(dto.getIndirizzoSede());
        s.setCitta(dto.getCitta());

        s.setTipologiaCondottaIllecita(Utility.listToString(dto.getTipologiaCondottaIllecita()));
        try {
            s.setDataAvvenimentoFatti(LocalDate.parse(dto.getDataAvvenimentoFatti(), Utility.FORMATTER));
        } catch (Exception e){

        }
        s.setFattiAncoraInCorso(dto.getFattiAncoraInCorso());
        s.setElencoSoggettiCoinvolti(convert(dto.getElencoSoggettiCoinvolti()));
        s.setDescrizioneFatti(dto.getDescrizioneFatti());

        s.setProcedimentoInAtto(dto.getProcedimentoInAtto());
        s.setConoscenzaProcedimento(dto.getConoscenzaProcedimento());
        s.setAutoritaRiferimento(dto.getAutoritaRiferimento());
        try {
            s.setDataEffettuazioneSegnalazione(LocalDate.parse(dto.getDataEffettuazioneSegnalazione(), Utility.FORMATTER));
        } catch (Exception e){
            e.printStackTrace();
        }
        s.setEstremiRegistrazione(dto.getEstremiRegistrazione());
        s.setDialogoParticolare(dto.getDialogoParticolare());
        s.setEsitoSegnalazione(dto.getEsitoSegnalazione());

        try {
            s.setCopiaEsposto(addUpdateAllegato(dto.getCopiaEsposto(), "Copia Esposto"));
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            s.setEvidenzeDocumentali(addUpdateAllegato(dto.getEvidenzeDoc(), dto.getDescrizioneEvidenzeDoc()));
            s.setEvidenzeMultimediali(addUpdateAllegato(dto.getEvidenzeMultimediali(), dto.getDescrizioneEvidenzeMultim()));
        } catch (Exception e){
            e.printStackTrace();
        }

        s.setIdentita(buildDatiUtente(dto));
        s.setNovantesimi(0);
        s.setStato(StatoSegnalazione.NON_ANCORA_PRESA_IN_CARICO.name());
        s.setAccettaTermini(dto.getAccettaTermini());

        return s;
    }

    private DatiUtente buildDatiUtente(SegnalazioneDto dto){
        DatiUtente du = null;
        Optional<DatiUtente> duOpt = datiUtenteRepository.findByCodiceFiscale(dto.getCodiceFiscale());
        if(duOpt.isPresent()){
            du = duOpt.get();
        } else {
            du = new DatiUtente();
            if(dto.getIsAnonimo()){
                du.setIsAnonimo(true);
            }else {
                du.setIsAnonimo(false);
                du.setCitta(dto.getCittaSegnalante());
                du.setEmail(dto.getEmail());
                du.setNome(dto.getNome());
                du.setTelefono(dto.getTelefono());
                du.setCognome(dto.getCognome());
                du.setCodiceFiscale(dto.getCodiceFiscale());
                du.setLuogoNascita(dto.getLuogoNascita());
                du.setMansione(dto.getMansione());
                du.setIndirizzo(dto.getIndirizzo());
                try {
                    du.setDataNascita(LocalDate.parse(dto.getDataNascita(), Utility.FORMATTER));
                }catch (Exception e){

                }
                du.setCodicePostale(dto.getCodicePostale());
                du.setTelefono(dto.getTelefono());
            }
            du = datiUtenteRepository.save(du);
        }
        return du;
    }

    private Allegato addUpdateAllegato(String file, String descrizione){
        Allegato a = new Allegato();
        a.setAllegato(Base64.getDecoder().decode(file));
        a.setDescrizione(descrizione);
        return allegatoRepository.save(a);
    }

    private List<SoggettoCoinvolto> convert(List<SoggettoCoinvoltoDto> dtoList){
        List<SoggettoCoinvolto> modelList = new ArrayList<>();
        for(SoggettoCoinvoltoDto dto : dtoList){
            SoggettoCoinvolto model = new SoggettoCoinvolto();
            model.setNome(dto.getNome());
            model.setNote(dto.getNote());
            model.setEnte(dto.getEnte());
            model.setEmail(dto.getEmail());
            model.setBeneficioAccaduto(dto.getBeneficioAccaduto());
            model.setTelefono(dto.getTelefono());
            model.setRuoloCoinvolgimento(dto.getRuoloCoinvolgimento());
            model.setRagioneSocialeCognome(dto.getRagioneSocialeCognome());
            model.setQualifica(dto.getQualifica());
            modelList.add(model);
        }
        return modelList;
    }

    private List<SoggettoCoinvoltoDto> convertModel(List<SoggettoCoinvolto> modelList){
        List<SoggettoCoinvoltoDto> dtoList = new ArrayList<>();
        for(SoggettoCoinvolto model : modelList){
            SoggettoCoinvoltoDto dto = new SoggettoCoinvoltoDto();
            dto.setNome(model.getNome());
            dto.setNote(model.getNote());
            dto.setEnte(model.getEnte());
            dto.setEmail(model.getEmail());
            dto.setBeneficioAccaduto(model.getBeneficioAccaduto());
            dto.setTelefono(model.getTelefono());
            dto.setRuoloCoinvolgimento(model.getRuoloCoinvolgimento());
            dto.setRagioneSocialeCognome(model.getRagioneSocialeCognome());
            dto.setQualifica(model.getQualifica());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public SegnalazioneDto getSegnalazioneByKey(String key16){
        if(key16.contains("-")) {
            key16 = key16.substring(0, 4).concat(key16.substring(5, 9)).concat(key16.substring(10, 14)).concat(key16.substring(15));
        }
        Segnalazione s = segnalazioneRepository.findByKey16(key16).get();
        SegnalazioneDto out = converter(s);
        return out;
    }

    private SegnalazioneDto converter(Segnalazione s){
        return SegnalazioneDto.builder()
                .id(s.getId())
                .key16(s.getKey16())
                .ruoloSegnalante(s.getRuoloSegnalante())
                .denominazioneCompletaUfficio(s.getDenominazioneCompletaUfficio())
                .ragioneSociale(s.getRagioneSociale())
                .indirizzoSede(s.getIndirizzoSede())
                .citta(s.getCitta())
                .tipologiaCondottaIllecita(Utility.stringToList(s.getTipologiaCondottaIllecita()))
                .dataAvvenimentoFatti(Utility.getDataFormattata(s.getDataAvvenimentoFatti()))
                .fattiAncoraInCorso(s.getFattiAncoraInCorso())
                .elencoSoggettiCoinvolti(convertModel(s.getElencoSoggettiCoinvolti()))
                .descrizioneFatti(s.getDescrizioneFatti())
                .procedimentoInAtto(s.getProcedimentoInAtto())
                .conoscenzaProcedimento(s.getConoscenzaProcedimento())
                .autoritaRiferimento(s.getAutoritaRiferimento())
                .dataEffettuazioneSegnalazione(Utility.getDataFormattata(s.getDataEffettuazioneSegnalazione()))
                .estremiRegistrazione(s.getEstremiRegistrazione())
                .dialogoParticolare(s.getDialogoParticolare())
                .esitoSegnalazione(s.getEsitoSegnalazione())
                .idCopiaEsposto(s.getCopiaEsposto() != null ? s.getCopiaEsposto().getId() : null)
                .idEvidenzeDoc(s.getEvidenzeDocumentali() != null ? s.getEvidenzeDocumentali().getId() : null)
                .descrizioneEvidenzeDoc(s.getEvidenzeDocumentali() != null ? s.getEvidenzeDocumentali().getDescrizione() : null)
                .idEvidenzeMultim(s.getEvidenzeMultimediali() != null ? s.getEvidenzeMultimediali().getId() : null)
                .descrizioneEvidenzeMultim(s.getEvidenzeMultimediali() != null ? s.getEvidenzeMultimediali().getDescrizione() : null)
                .isAnonimo(s.getIdentita().getIsAnonimo())
                .nome(s.getIdentita().getNome())
                .cognome(s.getIdentita().getCognome())
                .dataNascita(Utility.getDataFormattata(s.getIdentita().getDataNascita()))
                .luogoNascita(s.getIdentita().getLuogoNascita())
                .codiceFiscale(s.getIdentita().getCodiceFiscale())
                .indirizzo(s.getIdentita().getIndirizzo())
                .cittaSegnalante(s.getIdentita().getCitta())
                .codicePostale(s.getIdentita().getCodicePostale())
                .telefono(s.getIdentita().getTelefono())
                .email(s.getIdentita().getEmail())
                .mansione(s.getIdentita().getMansione())
                .accettaTermini(s.getAccettaTermini())
                .idInfo(infoSegnalazioneRepository.findByIdSegnalazione(s.getId()).getId())
                .novantesimi(s.getNovantesimi())
                .stato(s.getStato())
                .build();
    }

    public void gestisciSegnalazioniGiornalmente(){
        List<Segnalazione> segnalazioneList = segnalazioneRepository.findAll();
        for(Segnalazione s : segnalazioneList){
            if(s.getStato().equalsIgnoreCase(StatoSegnalazione.PRESA_IN_CARICO.name()) || s.getStato().equalsIgnoreCase(StatoSegnalazione.IN_ISTRUTTORIA.name())){
                if(s.getNovantesimi() != null){
                    s.setNovantesimi(s.getNovantesimi() + 1);
                } else {
                    s.setNovantesimi(0);
                }
                if(s.getNovantesimi() == 90){
                    s.setStato(StatoSegnalazione.ARCHIVIATA.name());
                }
                segnalazioneRepository.save(s);
            }
        }

        List<InfoSegnalazione> infoSegnalazioneList = infoSegnalazioneRepository.findAll();
        for(InfoSegnalazione is : infoSegnalazioneList){
            if(is.getStato().equalsIgnoreCase(StatoSegnalazione.PRESA_IN_CARICO.name()) || is.getStato().equalsIgnoreCase(StatoSegnalazione.IN_ISTRUTTORIA.name())){
                if(is.getNovantesimi() != null){
                    is.setNovantesimi(is.getNovantesimi() + 1);
                } else {
                    is.setNovantesimi(0);
                }
                if(is.getNovantesimi() == 90){
                    is.setStato(StatoSegnalazione.ARCHIVIATA.name());
                }
                infoSegnalazioneRepository.save(is);
            }
        }
    }
}
