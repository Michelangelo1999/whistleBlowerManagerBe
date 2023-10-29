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

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
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
        s.setDataAvvenimentoFatti(LocalDate.parse(dto.getDataAvvenimentoFatti(), Utility.FORMATTER));
        s.setFattiAncoraInCorso(dto.getFattiAncoraInCorso());
        s.setElencoSoggettiCoinvolti(convert(dto.getElencoSoggettiCoinvolti()));
        s.setDescrizioneFatti(dto.getDescrizioneFatti());

        s.setProcedimentoInAtto(dto.getProcedimentoInAtto());
        s.setConoscenzaProcedimento(dto.getConoscenzaProcedimento());
        s.setAutoritaRiferimento(dto.getAutoritaRiferimento());
        s.setDataEffettuazioneSegnalazione(LocalDate.parse(dto.getDataEffettuazioneSegnalazione(), Utility.FORMATTER));
        s.setEstremiRegistrazione(dto.getEstremiRegistrazione());
        s.setDialogoParticolare(dto.getDialogoParticolare());
        s.setEsitoSegnalazione(dto.getEsitoSegnalazione());
        s.setCopiaEsposto(Base64.getDecoder().decode(dto.getCopiaEsposto()));

        s.setEvidenzeDocumentali(addUpdateAllegato(dto.getEvidenzeDoc(), dto.getDescrizioneEvidenzeDoc()));
        s.setEvidenzeMultimediali(addUpdateAllegato(dto.getEvidenzeMultimediali(), dto.getDescrizioneEvidenzeDoc()));

        s.setIdentita(buildDatiUtente(dto));

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
                du.setDataNascita(LocalDate.parse(dto.getDataNascita(), Utility.FORMATTER));
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
        return converter(segnalazioneRepository.findByKey16(key16).get());
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
                .dataAvvenimentoFatti(s.getDataAvvenimentoFatti().format(Utility.FORMATTER))
                .fattiAncoraInCorso(s.getFattiAncoraInCorso())
                .elencoSoggettiCoinvolti(convertModel(s.getElencoSoggettiCoinvolti()))
                .descrizioneFatti(s.getDescrizioneFatti())
                .procedimentoInAtto(s.getProcedimentoInAtto())
                .conoscenzaProcedimento(s.getConoscenzaProcedimento())
                .autoritaRiferimento(s.getAutoritaRiferimento())
                .dataEffettuazioneSegnalazione(s.getDataEffettuazioneSegnalazione().format(Utility.FORMATTER))
                .estremiRegistrazione(s.getEstremiRegistrazione())
                .dialogoParticolare(s.getDialogoParticolare())
                .esitoSegnalazione(s.getEsitoSegnalazione())
                //.copiaEsposto()
                //.evidenzeDoc(s.getEvidenzeDocumentali().getDescrizione())
                .descrizioneEvidenzeDoc(s.getEvidenzeDocumentali().getDescrizione())
                //.evidenzeMultimediali()
                .descrizioneEvidenzeMultim(s.getEvidenzeMultimediali().getDescrizione())
                .isAnonimo(s.getIdentita().getIsAnonimo())
                .nome(s.getIdentita().getNome())
                .cognome(s.getIdentita().getCognome())
                .dataNascita(s.getIdentita().getDataNascita().format(Utility.FORMATTER))
                .luogoNascita(s.getIdentita().getLuogoNascita())
                .codiceFiscale(s.getIdentita().getCodiceFiscale())
                .indirizzo(s.getIdentita().getIndirizzo())
                .cittaSegnalante(s.getIdentita().getCitta())
                .codicePostale(s.getIdentita().getCodicePostale())
                .telefono(s.getIdentita().getTelefono())
                .email(s.getIdentita().getEmail())
                .mansione(s.getIdentita().getMansione())
                .accettaTermini(s.getAccettaTermini())
                .build();
    }
}
