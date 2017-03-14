package client.bean.chat.dao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHAT_FRIEND_BEAN".
*/
public class ChatFriendBeanDao extends AbstractDao<ChatFriendBean, String> {

    public static final String TABLENAME = "CHAT_FRIEND_BEAN";

    /**
     * Properties of entity ChatFriendBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Uid = new Property(0, String.class, "uid", true, "UID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Header = new Property(2, String.class, "header", false, "HEADER");
        public final static Property Nickname = new Property(3, String.class, "nickname", false, "NICKNAME");
        public final static Property Gid = new Property(4, String.class, "gid", false, "GID");
        public final static Property Type = new Property(5, int.class, "type", false, "TYPE");
    }

    private Query<ChatFriendBean> chatFriendGroupBean_FbsQuery;

    public ChatFriendBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ChatFriendBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHAT_FRIEND_BEAN\" (" + //
                "\"UID\" TEXT PRIMARY KEY NOT NULL ," + // 0: uid
                "\"NAME\" TEXT," + // 1: name
                "\"HEADER\" TEXT," + // 2: header
                "\"NICKNAME\" TEXT," + // 3: nickname
                "\"GID\" TEXT," + // 4: gid
                "\"TYPE\" INTEGER NOT NULL );"); // 5: type
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHAT_FRIEND_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ChatFriendBean entity) {
        stmt.clearBindings();
 
        String uid = entity.getUid();
        if (uid != null) {
            stmt.bindString(1, uid);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String header = entity.getHeader();
        if (header != null) {
            stmt.bindString(3, header);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(4, nickname);
        }
 
        String gid = entity.getGid();
        if (gid != null) {
            stmt.bindString(5, gid);
        }
        stmt.bindLong(6, entity.getType());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ChatFriendBean entity) {
        stmt.clearBindings();
 
        String uid = entity.getUid();
        if (uid != null) {
            stmt.bindString(1, uid);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String header = entity.getHeader();
        if (header != null) {
            stmt.bindString(3, header);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(4, nickname);
        }
 
        String gid = entity.getGid();
        if (gid != null) {
            stmt.bindString(5, gid);
        }
        stmt.bindLong(6, entity.getType());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public ChatFriendBean readEntity(Cursor cursor, int offset) {
        ChatFriendBean entity = new ChatFriendBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // uid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // header
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // nickname
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // gid
            cursor.getInt(offset + 5) // type
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ChatFriendBean entity, int offset) {
        entity.setUid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setHeader(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNickname(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGid(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setType(cursor.getInt(offset + 5));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ChatFriendBean entity, long rowId) {
        return entity.getUid();
    }
    
    @Override
    public String getKey(ChatFriendBean entity) {
        if(entity != null) {
            return entity.getUid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ChatFriendBean entity) {
        return entity.getUid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "fbs" to-many relationship of ChatFriendGroupBean. */
    public List<ChatFriendBean> _queryChatFriendGroupBean_Fbs(String gid) {
        synchronized (this) {
            if (chatFriendGroupBean_FbsQuery == null) {
                QueryBuilder<ChatFriendBean> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Gid.eq(null));
                chatFriendGroupBean_FbsQuery = queryBuilder.build();
            }
        }
        Query<ChatFriendBean> query = chatFriendGroupBean_FbsQuery.forCurrentThread();
        query.setParameter(0, gid);
        return query.list();
    }

}