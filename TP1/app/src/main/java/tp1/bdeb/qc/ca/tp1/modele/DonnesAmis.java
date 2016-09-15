package tp1.bdeb.qc.ca.tp1.modele;

/**
 * Created by Olga on 2015-09-25.
 */
public class DonnesAmis {
    private int id;
    private String nom;
    private String tel;
    private String email;
    private String contactId;

    public DonnesAmis(int id, String nom, String tel, String email, String contactId) {
        this.nom = nom;
        this.tel = tel;
        this.email = email;
        this.contactId = contactId;
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
