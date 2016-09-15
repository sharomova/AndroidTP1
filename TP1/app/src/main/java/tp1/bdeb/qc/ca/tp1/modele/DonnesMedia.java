package tp1.bdeb.qc.ca.tp1.modele;

/**
 * Created by Olga on 2015-09-26.
 */
public class DonnesMedia {
    private String nomMedia;
    private int coteMedia;
    private String datePret;
    private String datePrevuRetour;
    private String dateReelleRetour;
    private String typeMedia;
    private String nomAmis;
    private int idMedia;

    public DonnesMedia(int idMedia, String nomAmis, String nomMedia, int coteMedia,
                       String datePret, String datePrevuRetour, String dateReelleRetour,
                       String typeMedia) {
        this.nomMedia = nomMedia;
        this.coteMedia = coteMedia;
        this.datePret = datePret;
        this.datePrevuRetour = datePrevuRetour;
        this.dateReelleRetour = dateReelleRetour;
        this.typeMedia = typeMedia;
        this.nomAmis = nomAmis;
        this.idMedia = idMedia;
    }

    public int getIdMedia() {
        return idMedia;
    }

    public void setIdMedia(int idMedia) {
        this.idMedia = idMedia;
    }

    public String getIdAmis() {
        return nomAmis;
    }

    public void setIdAmis(String idAmis) {
        this.nomAmis = idAmis;
    }

    public String getTypeMedia() {
        return typeMedia;
    }

    public void setTypeMedia(String typeMedia) {
        this.typeMedia = typeMedia;
    }

    public String getDateReelleRetour() {
        return dateReelleRetour;
    }

    public void setDateReelleRetour(String dateReelleRetour) {
        this.dateReelleRetour = dateReelleRetour;
    }

    public String getDatePrevuRetour() {
        return datePrevuRetour;
    }

    public void setDatePrevuRetour(String datePrevuRetour) {
        this.datePrevuRetour = datePrevuRetour;
    }

    public String getDatePret() {
        return datePret;
    }

    public void setDatePret(String datePret) {
        this.datePret = datePret;
    }

    public int getCoteMedia() {
        return coteMedia;
    }

    public void setCoteMedia(int coteMedia) {
        this.coteMedia = coteMedia;
    }

    public String getNomMedia() {
        return nomMedia;
    }

    public void setNomMedia(String nomMedia) {
        this.nomMedia = nomMedia;
    }



}
