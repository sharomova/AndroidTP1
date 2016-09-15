package tp1.bdeb.qc.ca.tp1.modele;

/**
 * Created by Olga on 2015-10-17.
 */
public class TypeMedia {
    private String titreMedia;
    private int idMedia;

    public TypeMedia(int idMedia, String titreMedia) {
        this.idMedia = idMedia;
        this.titreMedia = titreMedia;
    }

    public int getIdMedia() {
        return idMedia;
    }

    public void setIdMedia(int idMedia) {
        this.idMedia = idMedia;
    }

    public String getTitreMedia() {
        return titreMedia;
    }

    public void setTitreMedia(String titreMedia) {
        this.titreMedia = titreMedia;
    }
}
