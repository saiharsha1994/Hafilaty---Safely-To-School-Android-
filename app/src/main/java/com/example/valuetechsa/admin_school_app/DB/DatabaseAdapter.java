package com.example.valuetechsa.admin_school_app.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter extends SQLiteOpenHelper {

	public DatabaseAdapter(Context context) {
		super(context, "admin.db", null, 3);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String language = "CREATE TABLE language(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "selected_language" + " TEXT,"
				+ "val" + " TEXT"+")";
		db.execSQL(language);

		String OneTimeLogin = "CREATE TABLE OneTimeLogin(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "EmailUser" + " TEXT,"
				+ "PasswordUser"+ " TEXT,"
				+ "AdminId" + " TEXT,"
				+ "AdminName" + " TEXT,"
				+ "SchoolLocation" + " TEXT"+")";
		db.execSQL(OneTimeLogin);

		String user = "CREATE TABLE user(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Teacher_Id" + " TEXT,"
				+ "Teacher_Name" + " TEXT,"
				+ "Class_Id"+ " TEXT"+")";
		db.execSQL(user);

		String gcmMsg = "CREATE TABLE gcmMsg(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "not_id" + " TEXT,"
				+ "msg"+ " TEXT"+")";
		db.execSQL(gcmMsg);
		
		String ForgotPassword = "CREATE TABLE ForgotPassword(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Email" + " TEXT,"
				+ "Status"+ " TEXT"+")";
		db.execSQL(ForgotPassword);

		String Album = "CREATE TABLE Album(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Album_Id" + " TEXT,"
				+ "Album_Title" + " TEXT," 
				+ "Album_Descript" + " TEXT," 
				+ "Album_Count" + " TEXT," 
				+ "Album_AddedOn"+ " TEXT"+")";
		db.execSQL(Album);

		String Classes = "CREATE TABLE Classes(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Class_Id" + " TEXT,"
				+ "Class_Name"+ " TEXT"+")";
		db.execSQL(Classes);
		
		String sections = "CREATE TABLE sections(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "section_id" + " TEXT,"
				+ "name" + " TEXT," 
				+ "class_id"+ " TEXT"+")";
		db.execSQL(sections);
		
		String Students = "CREATE TABLE Students(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Stu_Id" + " TEXT,"
				+ "Stu_Name" + " TEXT," 
				+ "Parent_Id"+ " TEXT"+")";
		db.execSQL(Students);

		String Attendance = "CREATE TABLE Attendance(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Att_Date" + " TEXT," 
				+ "Att_Month" + " TEXT," 
				+ "Class_Id" + " TEXT," 
				+ "Section_Id" + " TEXT," 
				+ "Teacher_Id" + " TEXT," 
				+ "Teacher_Name" + " TEXT," 
				+ "Stu_Id" + " TEXT," 
				+ "Stu_Name" + " TEXT," 
				+ "In_Status" + " TEXT," 
				+ "Out_Status"+ " TEXT"+")";
		db.execSQL(Attendance);

		String contactList = "CREATE TABLE contactList(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Chat_Id" + " INTEGER,"
				+ "PostBy_Id" + " TEXT,"
				+ "message_thread_code" + " TEXT,"
				+ "PostBy_Name"+ " TEXT"+")";
		db.execSQL(contactList);


		String ChatList = "CREATE TABLE ChatList(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Chat_Id" + " INTEGER," 
				+ "PostBy_Id" + " TEXT," 
				+ "PostBy_Name" + " TEXT," 
				+ "Message" + " TEXT," 
				+ "PostTo_Id" + " TEXT," 
				+ "PostTo_Name" + " TEXT,"
				+ "Post_Type" + " TEXT,"
				+ "Post_Url" + " TEXT,"
				+ "Post_On"+ " TEXT"+")";
		db.execSQL(ChatList);
		
		String AllContacts= "CREATE TABLE AllContacts(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Parent_Id" + " TEXT,"
				+ "Parent_Name"+ " TEXT"+")";
		db.execSQL(AllContacts);
		
		
		String AdminContacts= "CREATE TABLE AdminContacts(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Admin_Id" + " TEXT,"
				+ "Admin_Name"+ " TEXT"+")";
		db.execSQL(AdminContacts);
		
		
		String unreadChat = "CREATE TABLE unreadChat(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Chat_Id"+ " INTEGER"+")";
		db.execSQL(unreadChat);


		String Gallery = "CREATE TABLE Gallery(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Gal_Id" + " TEXT,"
				+ "Gal_Desc" + " TEXT,"
				+ "Gal_Img_Url" + " TEXT,"
				+ "Like_Count" + " TEXT,"
				+ "Album_Id" + " TEXT,"
				+ "Album_Title" + " TEXT,"
				+ "Post_Time"+ " TEXT"+")";
		db.execSQL(Gallery);
		
		String Posts = "CREATE TABLE Posts(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Post_Id" + " TEXT,"
				+ "Post_Title" + " TEXT," 
				+ "Post_Descript" + " TEXT," 
				+ "Post_Details" + " TEXT," 
				+ "Post_Type" + " TEXT," 
				+ "Post_ById" + " TEXT," 
				+ "Post_ByName" + " TEXT,"
				+ "Comment_Count" + " TEXT,"
				+ "Like_Count" + " TEXT,"
				+ "Post_Time"+ " TEXT"+")";
		db.execSQL(Posts);
		
		String Comments = "CREATE TABLE Comments(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Comment_Id" + " TEXT,"
				+ "Comment_Content" + " TEXT," 
				+ "Comment_ById" + " TEXT," 
				+ "Comment_ByName" + " TEXT," 
				+ "Post_Id" + " TEXT," 
				+ "Comment_On"+ " TEXT"+")";
		db.execSQL(Comments);

		String Likes = "CREATE TABLE Likes(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Like_Id" + " TEXT,"
				+ "Like_Status" + " TEXT," 
				+ "Like_ById" + " TEXT," 
				+ "Like_ByName" + " TEXT," 
				+ "Post_Id" + " TEXT," 
				+ "Liked_On"+ " TEXT"+")";
		db.execSQL(Likes);
		
		String Notices = "CREATE TABLE Notices(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Notice_Id" + " TEXT,"
				+ "Notice_Title" + " TEXT,"
				+ "Notice_Info" + " TEXT,"
				+ "Status" + " TEXT,"
				+ "Notice_Img" + " TEXT,"
				+ "Notice_Date"+ " TEXT"+")";
		db.execSQL(Notices);
		
		String DummyNotices = "CREATE TABLE DummyNotices(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Notice_Id" + " TEXT,"
				+ "Notice_Title" + " TEXT," 
				+ "Notice_Info" + " TEXT," 
				+ "Notice_Date" + " TEXT," 
				+ "Notice_Img" + " TEXT," 
				+ "Status"+ " TEXT"+")";
		db.execSQL(DummyNotices);
		
		String Events = "CREATE TABLE Events(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Event_Id" + " TEXT,"
				+ "Event_Title" + " TEXT," 
				+ "Event_Info" + " TEXT," 
				+ "Event_Date" + " TEXT," 
				+ "Event_AddedOn" + " TEXT,"
				+ "Last_UpdateOn"+ " TEXT"+")";
		db.execSQL(Events);
		
		String Curriculum = "CREATE TABLE Curriculum(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Cur_Id" + " TEXT,"
				+ "Cur_Title" + " TEXT,"
				+ "Cur_Desc" + " TEXT,"
				+ "Sub_Type" + " TEXT,"
				+ "Prg_Type" + " TEXT,"
				+ "Added_On"+ " TEXT"+")";
		db.execSQL(Curriculum);
		
		String subjects = "CREATE TABLE subjects(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Subject_Id" + " INTEGER,"
				+ "Subject_Name"+ " TEXT"+")";
		db.execSQL(subjects);
		
		String classRoutine = "CREATE TABLE classRoutine(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Class_Routine_Id" + " INTEGER,"
				+ "Class_Id" + " TEXT,"
				+ "Subject_Id" + " TEXT,"
				+ "Start_Time" + " TEXT,"
				+ "End_Time" + " TEXT,"
				+ "Teacher_Id" + " TEXT,"
				+ "Day" + " TEXT,"
				+ "Year"+ " TEXT"+")";
		db.execSQL(classRoutine);
		
		String homework = "CREATE TABLE homework(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Homework_Id" + " INTEGER,"
				+ "Subject_Id" + " TEXT,"
				+ "Title" + " TEXT,"
				+ "Description" + " TEXT,"
				+ "Date" + " TEXT,"
				+ "Due_Date"+ " TEXT"+")";
		db.execSQL(homework);
		
		String updates = "CREATE TABLE updates(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "Update_Id" + " INTEGER,"
				+ "Todays_Update" + " TEXT,"
				+ "Date" + " TEXT,"
				+ "Class_Id"+ " TEXT"+")";
		db.execSQL(updates);
		
		String Noticeboard = "CREATE TABLE Noticeboard(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "notice_id" + " TEXT,"
				+ "notice_title" + " TEXT,"
				+ "notice" + " TEXT,"
				+ "reciever" + " TEXT,"
				+ "create_timestamp"+ " TEXT"+")";
		db.execSQL(Noticeboard);
		
		String unReadCount = "CREATE TABLE unReadCount(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "message_thread_code" + " TEXT,"
				+ "read_status"+ " TEXT"+")";
		db.execSQL(unReadCount);
		
		String transport = "CREATE TABLE transport(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "name" + " TEXT,"
				+ "mobile" + " TEXT," 
				+ "photo" + " TEXT," 
				+ "route_name" + " TEXT," 
				+ "chassis_number" + " TEXT,"
				+ "plate_number" + " TEXT,"
				+ "bus_from" + " TEXT,"
				+ "bus_to"+ " TEXT"+")";
		db.execSQL(transport);

		String teacher_academic = "CREATE TABLE teacher_academic(" + "_id"+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "sno" + " INTEGER,"
				+ "academic_id" + " TEXT,"
				+ "day" + " TEXT,"
				+ "from_page" + " TEXT,"
				+ "to_page" + " TEXT,"
				+ "complete_by" + " TEXT,"
				+ "completed_on" + " TEXT,"
				+ "book_name"+ " TEXT"+")";
		db.execSQL(teacher_academic);

	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}