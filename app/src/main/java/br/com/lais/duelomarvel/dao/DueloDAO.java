package br.com.lais.duelomarvel.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import br.com.lais.duelomarvel.modelo.ResultsResponse;

/**
 * Created by Lais on 29/09/2016.
 */
public class DueloDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "DUELO";
    private static final String TABELADUELO = "TABELADUELO";
    private static final String TABELAPERSONAGEM = "TABELAPERSONAGEM";
    private static final String TABELATIME = "TABELATIME";
    private static final String TABELAPERSONAGEMTIME = "TABELAPERSONAGEMTIME";
    private static final int VERSAO = 1;
    private Context context;

    public DueloDAO(Context context) {
        super(context, DATABASE, null, VERSAO);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABELAPERSONAGEM +
                "(id INTEGER PRIMARY KEY," +
                " nome TEXT," +
                " potencial INTEGER" +
                ");";

        String sql2 = "CREATE TABLE " + TABELATIME +
                "(id INTEGER PRIMARY KEY," +
                " nome TEXT" +
                ");";

        String sql3 = "CREATE TABLE " + TABELAPERSONAGEMTIME +
                "(id INTEGER PRIMARY KEY," +
                " idPersonagem INTEGER," +
                " idTime INTEGER" +
                ");";

        String sql4 = "CREATE TABLE " + TABELADUELO +
                "(id INTEGER PRIMARY KEY," +
                " idTimeUm INTEGER," +
                " idTimeDois INTEGER," +
                " idTimeVencedor INTEGER" +
                ");";

        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELAPERSONAGEM;
        String sql2 = "DROP TABLE IF EXISTS " + TABELATIME;
        String sql3 = "DROP TABLE IF EXISTS " + TABELAPERSONAGEMTIME;
        String sql4 = "DROP TABLE IF EXISTS " + TABELADUELO;
        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        onCreate(db);
    }

    public void inserir(List<ResultsResponse> timeVitorioso, List<ResultsResponse> timePerdedor) {
        //inserindo registro tabela time
        long idTimeVitorioso = getWritableDatabase().insert(TABELATIME, null, transformaTime(timeVitorioso));
        long idTimePerdedor = getWritableDatabase().insert(TABELATIME, null, transformaTime(timePerdedor));

        //inserindo registro tabela duelo
        getWritableDatabase().insert(TABELADUELO, null, transformaDuelo(idTimeVitorioso,idTimePerdedor));

        //inserindo registro tabela persoagem e tabela relacionamento referente ao timeVitorioso
        for(ResultsResponse r : timeVitorioso){
            long idPersonagem = getWritableDatabase().insert(TABELAPERSONAGEM, null, transformaPersonagem(r));
            getWritableDatabase().insert(TABELAPERSONAGEMTIME,null,tranformaPersonagemTime(idTimeVitorioso,idPersonagem));
        }

        //inserindo registro tabela persoagem e tabela relacionamento referente ao timePerdedor
        for(ResultsResponse r : timePerdedor){
            long idPersonagem = getWritableDatabase().insert(TABELAPERSONAGEM, null, transformaPersonagem(r));
            getWritableDatabase().insert(TABELAPERSONAGEMTIME,null,tranformaPersonagemTime(idTimePerdedor,idPersonagem));
        }

    }

    private ContentValues tranformaPersonagemTime(long idTime, long idPersonagem) {
        ContentValues values = new ContentValues();
        values.put("idPersonagem", idPersonagem);
        values.put("idTime", idTime);


        return values;
    }

    private ContentValues transformaDuelo(long idTimeVitorioso, long idTimePerdedor) {
        ContentValues values = new ContentValues();

        values.put("idTimeUm", idTimeVitorioso);
        values.put("idTimeDois", idTimePerdedor);
        values.put("idTimeVencedor", idTimeVitorioso);

        return values;
    }

    private ContentValues transformaTime(List<ResultsResponse> timeVitorioso) {
        ContentValues values = new ContentValues();

        values.put("nome", timeVitorioso.get(0).getName());

        return values;
    }

    private ContentValues transformaPersonagem(ResultsResponse response) {

        ContentValues values = new ContentValues();

        values.put("nome", response.getName());
        values.put("potencial", response.getStories().getAvailable());


        return values;

    }
}
