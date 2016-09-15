package tp1.bdeb.qc.ca.tp1.modele;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Olga on 2015-10-06.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "app.db"; // Votre nom de BD
    public static final int DBVERSION = 1; // Votre numéro de version de BD
    private Context context;
    private static DbHelper instance = null; //L’unique instance de DbHelper possible
    // Mettre ici toutes les constantes de noms de tables et de colonnes
    private static final String TABLE_AMI = "amis";
    private static final String TABLE_MEDIA = "media";
    private static final String TABLE_TYPE_MEDIA = "type";
    // Noms de colonnes de TYPE MEDIA
    private static final String TYPE_MEDIA_ID = "_id";
    private static final String TYPE_MEDIA_TITRE = "titre";
    // Noms de colonnes d'AMI
    private static final String AMI_ID = "_id";
    private static final String AMI_NOM = "nom";
    private static final String AMI_TEL = "telephone";
    private static final String AMI_CONTACT_ID = "contacteid";
    private static final String AMI_COURRIEL = "courriel";
    // Noms de colonnes dE MEDIA
    private static final String MEDIA_ID = "_id";
    private static final String MEDIA_NOM = "nom";
    private static final String MEDIA_TYPE = "type_id";
    private static final String MEDIA_COTE = "cote";
    private static final String MEDIA_AMI_ID = "amisId";
    private static final String MEDIA_DATE_PRET = "datePret";
    private static final String MEDIA_DATE_PREVU = "datePrevuRetour";
    private static final String MEDIA_DATE_REELLE = "dateReelleRetour ";

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Constructeur de DBHelper
     */
    private DbHelper(Context context) {
        super(context, DB_NAME, null, DBVERSION);
        this.context = context;
    }

    /**
     * Appeler lors de l’appel « normal » de la création de BD
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlType = "CREATE TABLE " + TABLE_TYPE_MEDIA +
                "(" + TYPE_MEDIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TYPE_MEDIA_TITRE +
                " TEXT NOT NULL)";
        db.execSQL(sqlType);

        String sqlAmi = "CREATE TABLE " + TABLE_AMI +
                "(" + AMI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + AMI_NOM + " TEXT,"
                + AMI_TEL + " TEXT," + AMI_COURRIEL + " TEXT," + AMI_CONTACT_ID + " TEXT)";
        db.execSQL(sqlAmi);

        String sqlMedia = "CREATE TABLE " + TABLE_MEDIA +
                "(" + MEDIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + MEDIA_AMI_ID + " INTEGER,"
                + MEDIA_NOM + " TEXT NOT NULL," + MEDIA_COTE + " INTEGER," + MEDIA_DATE_PRET +
                " TEXT," + MEDIA_DATE_PREVU + " TEXT," + MEDIA_DATE_REELLE + " TEXT," + MEDIA_TYPE +
                " INTEGER, FOREIGN KEY(" + MEDIA_TYPE + ") REFERENCES "
                + TABLE_TYPE_MEDIA + " (" + TYPE_MEDIA_ID + ")" + ")";
        db.execSQL(sqlMedia);

        //ajouter les amis par defaut pour verifier
        ajouterAmiDefaut(db, "Victor Loup", "5145168765", "victor@gmail.com", "");
        ajouterAmiDefaut(db, "Eva Belle", "5142356783", "evabelle@gmail.com", "");
        ajouterAmiDefaut(db, "Arnolde Jonsone", "5142358905", "victor@gmail.com", "");
        ajouterAmiDefaut(db, "Vuctoria Faste", "5145169999", "victoriafast@gmail.com", "");
        ajouterAmiDefaut(db, "Mikle Softe", "5145161717", "miklsoft@gmail.com", "");
        ajouterAmiDefaut(db, "Arnie Victoire", "5142358888", "arnievictor@gmail.com", "");
        ajouterAmiDefaut(db, "Selma Hugoste", "5145168777", "hugoste@gmail.com", "");
        ajouterAmiDefaut(db, "Jons Jonsones", "5142356788", "jonsons@gmail.com", "");
        ajouterAmiDefaut(db, "Borice Kvakov", "5142355555", "kvakovborr@gmail.com", "");
        //ajouter les type de media par defaut
        ajouterTypeMedia(db, "Filme");
        ajouterTypeMedia(db, "Jeu");
        ajouterTypeMedia(db, "Autre");
        //ajouter les media par defaut pour verifier
        ajouterMediaDefaut(db, 1, "Titanic", 5, "03/12/2015", "03/28/2015", "04/04/2015", 1);
        ajouterMediaDefaut(db, 111, "Varvar", 2, "", "", "", 3);
        ajouterMediaDefaut(db, 3, "Angry Birds", 4, "03/12/2015", "12/25/2015", "", 2);
        ajouterMediaDefaut(db, 2, "Paule au Quebec", 3, "03/12/2015", "11/28/2015", "", 1);
    }

    /**
     * methode qui ajoute les données dans la table type de media
     * @param db
     * @param type
     */
    public void ajouterTypeMedia(SQLiteDatabase db, String type) {
        ContentValues values = new ContentValues();
        values.put(TYPE_MEDIA_TITRE, type);
        // Insérer le nouvel enregistrement
        long id = db.insert(TABLE_TYPE_MEDIA, null, values);

    }
    /**
     * methode qui ajoute les amis par defaut
     *
     * @param nom
     * @param tel
     * @param courriel
     */

    private void ajouterAmiDefaut(SQLiteDatabase db, String nom, String tel, String courriel,
                                  String contId) {
        ContentValues values = new ContentValues();
        values.put(AMI_NOM, nom); // Nom d'ami
        values.put(AMI_TEL, tel); // Tel d'ami
        values.put(AMI_COURRIEL, courriel); // Courriel d'ami
        values.put(AMI_CONTACT_ID, contId); // idContacte d'ami

// Insérer le nouvel enregistrement
        long id = db.insert(TABLE_AMI, null, values);
    }

    /**
     * methode que ajoute les donnée media par defaut pour verifier
     * @param db
     * @param nomAmi
     * @param nom
     * @param cote
     * @param datePret
     * @param datePrevu
     * @param dateReelle
     * @param type
     */
    private void ajouterMediaDefaut(SQLiteDatabase db, int nomAmi, String nom,
                                    int cote, String datePret, String datePrevu,
                                    String dateReelle, int type) {
        ContentValues values = new ContentValues();
        values.put(MEDIA_AMI_ID, nomAmi);
        values.put(MEDIA_NOM, nom);
        values.put(MEDIA_COTE, cote);
        values.put(MEDIA_DATE_PRET, datePret);
        values.put(MEDIA_DATE_PREVU, datePrevu);
        values.put(MEDIA_DATE_REELLE, dateReelle);
        values.put(MEDIA_TYPE, type);

// Insérer le nouvel enregistrement
        long id = db.insert(TABLE_MEDIA, null, values);
    }

    /**
     * methode qui ajoute un media de la vue
     * @param media
     * @return id d'ami
     */
    public int ajouterMedia(DonnesMedia media) {
        int idType = getIdMedia(media.getTypeMedia());
        int idAmi = -1;
        if (!media.getIdAmis().equals("")) {
            idAmi = getIdAmis(media.getIdAmis());
            //les media empruntées
            if (idAmi != -1 && idType != -1) {
                SQLiteDatabase db = this.getWritableDatabase(); // On veut écrire dans la BD
                ContentValues values = new ContentValues();
                values.put(MEDIA_AMI_ID, idAmi);
                values.put(MEDIA_NOM, media.getNomMedia());
                values.put(MEDIA_COTE, media.getCoteMedia());
                values.put(MEDIA_DATE_PRET, media.getDatePret());
                values.put(MEDIA_DATE_PREVU, media.getDatePrevuRetour());
                values.put(MEDIA_DATE_REELLE, media.getDateReelleRetour());
                values.put(MEDIA_TYPE, idType);

// Insérer le nouvel enregistrement
                long id = db.insert(TABLE_MEDIA, null, values);
                db.close(); // Fermer la connexion
            }
        } else {
            //les propres média
            SQLiteDatabase db = this.getWritableDatabase(); // On veut écrire dans la BD
            ContentValues values = new ContentValues();
            values.put(MEDIA_AMI_ID, 111);
            values.put(MEDIA_NOM, media.getNomMedia());
            values.put(MEDIA_COTE, media.getCoteMedia());
            values.put(MEDIA_DATE_PRET, "");
            values.put(MEDIA_DATE_PREVU, "");
            values.put(MEDIA_DATE_REELLE, "");
            values.put(MEDIA_TYPE, idType);

// Insérer le nouvel enregistrement
            long id = db.insert(TABLE_MEDIA, null, values);
            db.close(); // Fermer la connexion
        }
        return idAmi;
    }

    /**
     * method qui ajoute un ami de la vue
     *
     * @param ami
     */

    public void ajouterAmi(DonnesAmis ami) {
        SQLiteDatabase db = this.getWritableDatabase(); // On veut écrire dans la BD
        ContentValues values = new ContentValues();
        values.put(AMI_NOM, ami.getNom());
        values.put(AMI_COURRIEL, ami.getEmail());
        values.put(AMI_TEL, ami.getTel());
// Insérer le nouvel enregistrement
        long id = db.insert(TABLE_AMI, null, values);
        db.close(); // Fermer la connexion
    }

    /**
     * methode qui cherche le id d'ami à partir de son nom
     * @param nom
     * @return id d'ami
     */
    public int getIdAmis(String nom) {
        SQLiteDatabase db = this.getReadableDatabase(); // On veut lire dans la BD
        int id = -1;
        String selectQuery = "SELECT  * FROM " + TABLE_AMI;
        Log.e(DB_NAME, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do {
                if (cursor.getString(1).equals(nom)) {
                    id = cursor.getInt(0);
                }
            } while (cursor.moveToNext());
        }
        db.close(); // Fermer la connexion
        return id;
    }

    /**
     * methode qui cherche le id de type média
     * @param type
     * @return id type de média
     */
    public int getIdMedia(String type) {
        SQLiteDatabase db = this.getReadableDatabase(); // On veut lire dans la BD
        String selectQuery = "SELECT  * FROM " + TABLE_TYPE_MEDIA;
        int idType = -1;
        Log.e(DB_NAME, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                if (type.equals(cursor.getString(1).toString())) {
                    idType = cursor.getInt(0);
                }

            } while (cursor.moveToNext());
        }
        db.close(); // Fermer la connexion
        return (idType);
    }

    /**
     * methode qui prende tout les médias pour afficher
     * @return la liste de média
     */
    public ArrayList<DonnesMedia> getTousMedia() {
        SQLiteDatabase db = this.getReadableDatabase(); // On veut lire dans la BD
        ArrayList<DonnesMedia> media = new ArrayList<DonnesMedia>();
        String selectQuery = "SELECT  * FROM " + TABLE_MEDIA;

        Log.e(DB_NAME, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                TypeMedia type = getTypeMedia(cursor.getInt(7));
                if (cursor.getInt(1) != 111) {
                    DonnesAmis ami = getAmi(cursor.getInt(1));
                    media.add(new DonnesMedia(cursor.getInt(0), ami.getNom().toString(),
                            cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                            cursor.getString(5), cursor.getString(6),
                            type.getTitreMedia().toString()));
                } else {
                    media.add(new DonnesMedia(cursor.getInt(0), "",
                            cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                            cursor.getString(5), cursor.getString(6),
                            type.getTitreMedia().toString()));
                }
            } while (cursor.moveToNext());
        }
        db.close(); // Fermer la connexion
        return (media);
    }
    /**
     * methode qui prende les médias empruntées pour afficher
     * @return la liste de média
     */
    public ArrayList<DonnesMedia> getEmprunteMedia() {
        SQLiteDatabase db = this.getReadableDatabase(); // On veut lire dans la BD
        ArrayList<DonnesMedia> media = new ArrayList<DonnesMedia>();
        String selectQuery = "SELECT  * FROM " + TABLE_MEDIA;

        Log.e(DB_NAME, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                if (!cursor.getString(4).equals("")) {
                    DonnesAmis ami = getAmi(cursor.getInt(1));
                    TypeMedia type = getTypeMedia(cursor.getInt(7));
                    media.add(new DonnesMedia(cursor.getInt(0), ami.getNom().toString(),
                            cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                            cursor.getString(5), cursor.getString(6),
                            type.getTitreMedia().toString()));
                }
            } while (cursor.moveToNext());
        }
        db.close(); // Fermer la connexion
        return (media);
    }

    /**
     * methode qui prende tous les amis dans la base de donnes
     *
     * @return la liste des amis
     */
    public ArrayList<DonnesAmis> getTousAmis() {
        SQLiteDatabase db = this.getReadableDatabase(); // On veut lire dans la BD
        ArrayList<DonnesAmis> ami = new ArrayList<DonnesAmis>();
        String selectQuery = "SELECT  * FROM " + TABLE_AMI;

        Log.e(DB_NAME, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do {
                ami.add(new DonnesAmis(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4)));

            } while (cursor.moveToNext());
        }
        db.close(); // Fermer la connexion
        return (ami);
    }

    /**
     * methode qui prende les donnes d'un ami
     *
     * @param id
     * @return les donnes d'un ami
     */
    public DonnesAmis getAmi(int id) {
        SQLiteDatabase db = this.getReadableDatabase(); // On veut lire dans la BD
        Cursor cursor = db.query(TABLE_AMI, new String[]{AMI_ID,
                        AMI_NOM, AMI_TEL, AMI_COURRIEL, AMI_CONTACT_ID}, AMI_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        DonnesAmis ami = new DonnesAmis(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4));
        cursor.close();
        db.close(); // Fermer la connexion
// Retourner l'ami
        return ami;
    }

    /**
     * methode qui prende un media
     *
     * @param id
     * @return un media
     */
    public DonnesMedia getMedia(int id) {
        DonnesMedia media;
        SQLiteDatabase db = this.getReadableDatabase(); // On veut lire dans la BD
        Cursor cursor = db.query(TABLE_MEDIA, new String[]{MEDIA_ID,
                        MEDIA_AMI_ID, MEDIA_NOM, MEDIA_COTE, MEDIA_DATE_PRET,
                        MEDIA_DATE_PREVU, MEDIA_DATE_REELLE, MEDIA_TYPE}, MEDIA_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        TypeMedia type = getTypeMedia(cursor.getInt(7));
        if (cursor.getInt(1) != 111) {
            DonnesAmis ami = getAmi(cursor.getInt(1));
            media = new DonnesMedia(cursor.getInt(0), ami.getNom().toString(),
                    cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), type.getTitreMedia().toString());
        } else {
            media = new DonnesMedia(cursor.getInt(0), "",
                    cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), type.getTitreMedia().toString());
        }
        cursor.close();
        db.close(); // Fermer la connexion
// Retourner le media
        return media;
    }

    /**
     * methode qui cherche le type de média
     * @param id
     * @return type
     */
    public TypeMedia getTypeMedia(int id) {
        SQLiteDatabase db = this.getReadableDatabase(); // On veut lire dans la BD
        Cursor cursor = db.query(TABLE_TYPE_MEDIA, new String[]{TYPE_MEDIA_ID,
                        TYPE_MEDIA_TITRE}, TYPE_MEDIA_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        TypeMedia type = new TypeMedia(cursor.getInt(0), cursor.getString(1));
        cursor.close();
        db.close(); // Fermer la connexion
// Retourner le media
        return type;
    }

    /**
     * methode qui modifie les données d'un ami
     *
     * @param ami
     * @return resultat
     */
    public boolean updateAmi(DonnesAmis ami) {
        SQLiteDatabase db = this.getWritableDatabase(); // On veut écrire dans la BD
        ContentValues values = new ContentValues();
        values.put(AMI_NOM, ami.getNom()); // Nom d'ami
        values.put(AMI_TEL, ami.getTel()); // telephone d'ami
        values.put(AMI_COURRIEL, ami.getEmail()); // email d'ami
        values.put(AMI_CONTACT_ID, ami.getContactId());

// MAJ de l’enregistrement
        int nbMAJ = db.update(TABLE_AMI, values, AMI_ID + " = ?",
                new String[]{String.valueOf(ami.getId())});
        db.close(); // Fermer la connexion
        return (nbMAJ > 0); // True si update, false sinon
    }

    /**
     * methode methode qui modifie les données de média
     *
     * @param media
     * @return resultat
     */

    public boolean updateMedia(DonnesMedia media) {
        int nbMAJ = -1;
        int idAmi = getIdAmis(media.getIdAmis());
        int idType = getIdMedia(media.getTypeMedia());
        if (idAmi != -1 && idType != -1) {
            SQLiteDatabase db = this.getWritableDatabase(); // On veut écrire dans la BD
            ContentValues values = new ContentValues();
            values.put(MEDIA_AMI_ID, idAmi);
            values.put(MEDIA_NOM, media.getNomMedia());
            values.put(MEDIA_COTE, media.getCoteMedia());
            values.put(MEDIA_DATE_PRET, media.getDatePret());
            values.put(MEDIA_DATE_PREVU, media.getDatePrevuRetour());
            values.put(MEDIA_DATE_REELLE, media.getDateReelleRetour());
            values.put(MEDIA_TYPE, idType);

// MAJ de l’enregistrement
            nbMAJ = db.update(TABLE_MEDIA, values, MEDIA_ID + " = ?",
                    new String[]{String.valueOf(media.getIdMedia())});
            db.close(); // Fermer la connexion
        }
        return (nbMAJ > 0); // True si update, false sinon
    }

    /**
     * Appeler lors d’un changement de version de notre BD
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Mettre les modifications de votre BD ici
    }
}
