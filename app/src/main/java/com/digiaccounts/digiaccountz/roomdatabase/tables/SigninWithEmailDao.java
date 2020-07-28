package com.digiaccounts.digiaccountz.roomdatabase.tables;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface SigninWithEmailDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void addDetails(SigninWithemailTable detail);

    @Query("SELECT * FROM useremail_table")
    public SigninWithemailTable[] loadAllUsers();


    @Query("SELECT * FROM useremail_table WHERE mobilenumber=:number")
    public SigninWithemailTable[] loadAllUsersaccordingtoEmail(String number);

    @Query("SELECT * FROM useremail_table WHERE mobilenumber=:mobileno AND pasword=:pas")
    public SigninWithemailTable[] loadAllUserLogin(String mobileno,String pas);

    @Query("SELECT * FROM useremail_table WHERE id=:idd")
    public SigninWithemailTable loadUserByID(long idd);

    @Query("UPDATE useremail_table SET fullname=:name,pasword=:password,email=:mail WHERE id=:idd")
    public int UpdateUserInfo(long idd,String name,String password,String mail);


}
