package haoshi.com.shop.bean.chat.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import haoshi.com.shop.bean.chat.FileBean;
import haoshi.com.shop.bean.chat.PhotoBean;
import haoshi.com.shop.bean.chat.SoundBean;
import haoshi.com.shop.bean.chat.TextBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHAT_VIEW_BEAN".
*/
public class ChatViewBeanDao extends AbstractDao<ChatViewBean, String> {

    public static final String TABLENAME = "CHAT_VIEW_BEAN";

    /**
     * Properties of entity ChatViewBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Sign = new Property(0, String.class, "sign", true, "SIGN");
        public final static Property Fid = new Property(1, String.class, "fid", false, "FID");
        public final static Property Type = new Property(2, int.class, "type", false, "TYPE");
        public final static Property Time = new Property(3, long.class, "time", false, "TIME");
        public final static Property IsRead = new Property(4, int.class, "isRead", false, "IS_READ");
        public final static Property Status = new Property(5, int.class, "status", false, "STATUS");
        public final static Property From = new Property(6, int.class, "from", false, "FROM");
    }

    private DaoSession daoSession;


    public ChatViewBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ChatViewBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHAT_VIEW_BEAN\" (" + //
                "\"SIGN\" TEXT PRIMARY KEY NOT NULL ," + // 0: sign
                "\"FID\" TEXT," + // 1: fid
                "\"TYPE\" INTEGER NOT NULL ," + // 2: type
                "\"TIME\" INTEGER NOT NULL ," + // 3: time
                "\"IS_READ\" INTEGER NOT NULL ," + // 4: isRead
                "\"STATUS\" INTEGER NOT NULL ," + // 5: status
                "\"FROM\" INTEGER NOT NULL );"); // 6: from
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHAT_VIEW_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ChatViewBean entity) {
        stmt.clearBindings();
 
        String sign = entity.getSign();
        if (sign != null) {
            stmt.bindString(1, sign);
        }
 
        String fid = entity.getFid();
        if (fid != null) {
            stmt.bindString(2, fid);
        }
        stmt.bindLong(3, entity.getType());
        stmt.bindLong(4, entity.getTime());
        stmt.bindLong(5, entity.getIsRead());
        stmt.bindLong(6, entity.getStatus());
        stmt.bindLong(7, entity.getFrom());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ChatViewBean entity) {
        stmt.clearBindings();
 
        String sign = entity.getSign();
        if (sign != null) {
            stmt.bindString(1, sign);
        }
 
        String fid = entity.getFid();
        if (fid != null) {
            stmt.bindString(2, fid);
        }
        stmt.bindLong(3, entity.getType());
        stmt.bindLong(4, entity.getTime());
        stmt.bindLong(5, entity.getIsRead());
        stmt.bindLong(6, entity.getStatus());
        stmt.bindLong(7, entity.getFrom());
    }

    @Override
    protected final void attachEntity(ChatViewBean entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public ChatViewBean readEntity(Cursor cursor, int offset) {
        ChatViewBean entity = new ChatViewBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // sign
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // fid
            cursor.getInt(offset + 2), // type
            cursor.getLong(offset + 3), // time
            cursor.getInt(offset + 4), // isRead
            cursor.getInt(offset + 5), // status
            cursor.getInt(offset + 6) // from
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ChatViewBean entity, int offset) {
        entity.setSign(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setFid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setType(cursor.getInt(offset + 2));
        entity.setTime(cursor.getLong(offset + 3));
        entity.setIsRead(cursor.getInt(offset + 4));
        entity.setStatus(cursor.getInt(offset + 5));
        entity.setFrom(cursor.getInt(offset + 6));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ChatViewBean entity, long rowId) {
        return entity.getSign();
    }
    
    @Override
    public String getKey(ChatViewBean entity) {
        if(entity != null) {
            return entity.getSign();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ChatViewBean entity) {
        return entity.getSign() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getSoundBeanDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getTextBeanDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T2", daoSession.getFileBeanDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T3", daoSession.getPhotoBeanDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T4", daoSession.getChatFriendBeanDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T5", daoSession.getSendBeanDao().getAllColumns());
            builder.append(" FROM CHAT_VIEW_BEAN T");
            builder.append(" LEFT JOIN SOUND_BEAN T0 ON T.\"SIGN\"=T0.\"SIGN\"");
            builder.append(" LEFT JOIN TEXT_BEAN T1 ON T.\"SIGN\"=T1.\"SIGN\"");
            builder.append(" LEFT JOIN FILE_BEAN T2 ON T.\"SIGN\"=T2.\"SIGN\"");
            builder.append(" LEFT JOIN PHOTO_BEAN T3 ON T.\"SIGN\"=T3.\"SIGN\"");
            builder.append(" LEFT JOIN CHAT_FRIEND_BEAN T4 ON T.\"FID\"=T4.\"FID\"");
            builder.append(" LEFT JOIN SEND_BEAN T5 ON T.\"SIGN\"=T5.\"SIGN\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected ChatViewBean loadCurrentDeep(Cursor cursor, boolean lock) {
        ChatViewBean entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        SoundBean soundBean = loadCurrentOther(daoSession.getSoundBeanDao(), cursor, offset);
        entity.setSoundBean(soundBean);
        offset += daoSession.getSoundBeanDao().getAllColumns().length;

        TextBean textBean = loadCurrentOther(daoSession.getTextBeanDao(), cursor, offset);
        entity.setTextBean(textBean);
        offset += daoSession.getTextBeanDao().getAllColumns().length;

        FileBean fileBean = loadCurrentOther(daoSession.getFileBeanDao(), cursor, offset);
        entity.setFileBean(fileBean);
        offset += daoSession.getFileBeanDao().getAllColumns().length;

        PhotoBean photoBean = loadCurrentOther(daoSession.getPhotoBeanDao(), cursor, offset);
        entity.setPhotoBean(photoBean);
        offset += daoSession.getPhotoBeanDao().getAllColumns().length;

        ChatFriendBean fUser = loadCurrentOther(daoSession.getChatFriendBeanDao(), cursor, offset);
        entity.setFUser(fUser);
        offset += daoSession.getChatFriendBeanDao().getAllColumns().length;

        SendBean sendBean = loadCurrentOther(daoSession.getSendBeanDao(), cursor, offset);
        entity.setSendBean(sendBean);

        return entity;    
    }

    public ChatViewBean loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<ChatViewBean> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<ChatViewBean> list = new ArrayList<ChatViewBean>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<ChatViewBean> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<ChatViewBean> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
