package client.bean.chat;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import client.bean.chat.dao.DaoSession;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PHOTO_BEAN".
*/
public class PhotoBeanDao extends AbstractDao<PhotoBean, String> {

    public static final String TABLENAME = "PHOTO_BEAN";

    /**
     * Properties of entity PhotoBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Sign = new Property(0, String.class, "sign", true, "SIGN");
        public final static Property Url_path = new Property(1, String.class, "url_path", false, "URL_PATH");
        public final static Property W = new Property(2, int.class, "w", false, "W");
        public final static Property H = new Property(3, int.class, "h", false, "H");
    }


    public PhotoBeanDao(DaoConfig config) {
        super(config);
    }
    
    public PhotoBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PHOTO_BEAN\" (" + //
                "\"SIGN\" TEXT PRIMARY KEY NOT NULL ," + // 0: sign
                "\"URL_PATH\" TEXT," + // 1: url_path
                "\"W\" INTEGER NOT NULL ," + // 2: w
                "\"H\" INTEGER NOT NULL );"); // 3: h
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PHOTO_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PhotoBean entity) {
        stmt.clearBindings();
 
        String sign = entity.getSign();
        if (sign != null) {
            stmt.bindString(1, sign);
        }
 
        String url_path = entity.getUrl_path();
        if (url_path != null) {
            stmt.bindString(2, url_path);
        }
        stmt.bindLong(3, entity.getW());
        stmt.bindLong(4, entity.getH());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PhotoBean entity) {
        stmt.clearBindings();
 
        String sign = entity.getSign();
        if (sign != null) {
            stmt.bindString(1, sign);
        }
 
        String url_path = entity.getUrl_path();
        if (url_path != null) {
            stmt.bindString(2, url_path);
        }
        stmt.bindLong(3, entity.getW());
        stmt.bindLong(4, entity.getH());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public PhotoBean readEntity(Cursor cursor, int offset) {
        PhotoBean entity = new PhotoBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // sign
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // url_path
            cursor.getInt(offset + 2), // w
            cursor.getInt(offset + 3) // h
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PhotoBean entity, int offset) {
        entity.setSign(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUrl_path(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setW(cursor.getInt(offset + 2));
        entity.setH(cursor.getInt(offset + 3));
     }
    
    @Override
    protected final String updateKeyAfterInsert(PhotoBean entity, long rowId) {
        return entity.getSign();
    }
    
    @Override
    public String getKey(PhotoBean entity) {
        if(entity != null) {
            return entity.getSign();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PhotoBean entity) {
        return entity.getSign() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}