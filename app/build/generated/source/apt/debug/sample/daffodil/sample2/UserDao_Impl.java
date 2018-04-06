package sample.daffodil.sample2;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import sample.daffodil.sample2.Database.User;
import sample.daffodil.sample2.Database.UserDao;

public class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUser;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `User`(`userId`,`email_id`,`first_name`,`last_name`,`mobile`,`password`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.userId);
        if (value.email == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.email);
        }
        if (value.firstName == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.firstName);
        }
        if (value.lastName == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.lastName);
        }
        if (value.mobile == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.mobile);
        }
        if (value.password == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.password);
        }
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `User` WHERE `userId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.userId);
      }
    };
  }

  @Override
  public void insert(User user) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(User user) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUser.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<User> getUserDetails(String email) {
    final String _sql = "SELECT * FROM user WHERE email_id LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("userId");
      final int _cursorIndexOfEmail = _cursor.getColumnIndexOrThrow("email_id");
      final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("first_name");
      final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("last_name");
      final int _cursorIndexOfMobile = _cursor.getColumnIndexOrThrow("mobile");
      final int _cursorIndexOfPassword = _cursor.getColumnIndexOrThrow("password");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item;
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        final String _tmpFirstName;
        _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        final String _tmpLastName;
        _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
        final String _tmpMobile;
        _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
        final String _tmpPassword;
        _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        _item = new User(_tmpEmail,_tmpFirstName,_tmpLastName,_tmpMobile,_tmpPassword);
        _item.userId = _cursor.getInt(_cursorIndexOfUserId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<User> getAll() {
    final String _sql = "SELECT * FROM user";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("userId");
      final int _cursorIndexOfEmail = _cursor.getColumnIndexOrThrow("email_id");
      final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("first_name");
      final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("last_name");
      final int _cursorIndexOfMobile = _cursor.getColumnIndexOrThrow("mobile");
      final int _cursorIndexOfPassword = _cursor.getColumnIndexOrThrow("password");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item;
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        final String _tmpFirstName;
        _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        final String _tmpLastName;
        _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
        final String _tmpMobile;
        _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
        final String _tmpPassword;
        _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        _item = new User(_tmpEmail,_tmpFirstName,_tmpLastName,_tmpMobile,_tmpPassword);
        _item.userId = _cursor.getInt(_cursorIndexOfUserId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<User> getPhoneDetails(String mob) {
    final String _sql = "SELECT * FROM user WHERE mobile LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (mob == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, mob);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("userId");
      final int _cursorIndexOfEmail = _cursor.getColumnIndexOrThrow("email_id");
      final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("first_name");
      final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("last_name");
      final int _cursorIndexOfMobile = _cursor.getColumnIndexOrThrow("mobile");
      final int _cursorIndexOfPassword = _cursor.getColumnIndexOrThrow("password");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item;
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        final String _tmpFirstName;
        _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        final String _tmpLastName;
        _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
        final String _tmpMobile;
        _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
        final String _tmpPassword;
        _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        _item = new User(_tmpEmail,_tmpFirstName,_tmpLastName,_tmpMobile,_tmpPassword);
        _item.userId = _cursor.getInt(_cursorIndexOfUserId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
