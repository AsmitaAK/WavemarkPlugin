package org.apache.cordova.wavemark;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 2;

	// Database Name
	public static final String DATABASE_NAME = "ngage";

	// Contacts table name
	public static final String TABLE_CONTACTS = "sfeeds";
	public static final String TABLE_CONTACTS1 = "newsfeeds";
	public static final String TABLE_CONTACTS2 = "favfeeds";
	public static final String TABLE_CONTACTS3 = "newsfav";
	public static final String TABLE_CONTACTS4 = "celebfeeds";
	public static final String FAVFOLDERS = "favFolders";
	public static final String BLOCKFOLDERS = "blockfolders";

	// Contacts Table Columns names
	public  static final String KEY_ID = "id";
	public static final String KEY_posttype = "posttype";
	public static final String KEY_title = "title";
	public static final String KEY_postcontent = "postcontent";
	public static final String KEY_attachment = "attachment";
	public static final String KEY_attachment_type = "attachment_type";
	public static final String KEY_avatar = "avatar";
	public static final String KEY_share_count = "share_count";
	public static final String KEY_like_count = "like_count";
	public static final String KEY_reward_points = "reward_points";
	public static final String KEY_news_type = "news_type";
	public static final String KEY_wp_id = "wpid";
	public static final String KEY_sponsorid = "sponsorid";
	public static final String FOLDER_ID = "folder_id";
	public static final String KEY_seen = "seen";

	public static final String KEY_FOLDER = "foldername";

	private static final String KEY_FOLDERTYPE = "foldertype";




	public static final String TABLE_WAVEMARK = "wavemark";
	public static  String PROJECT_ID = "project_id";
	public static  String PROJECT_START_DATE = "project_start_date";
	public static  String PROJECT_END_DATE = "project_end_date";
	public static  String PROJECT_START_TIME = "project_start_time";
	public static  String PROJECT_END_TIME = "project_end_time";
	public static String EXPIRY_MESSAGE = "expiry_message";
	public static  String KEY_description = "description";
	public static  String KEY_error_code = "errorcode";
	public static  String KEY_id = "idunique";
	public static  String KEY_image_id = "imageid";
	public static  String KEY_image_url = "imageurl";
	public static  String KEY_meta_data = "metadata";
	public static  String KEY_status = "keystatus";
	public static  String KEY_url = "keyurl";



	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
			+ KEY_ID+ " INTEGER PRIMARY KEY,"
			+ KEY_posttype + " TEXT,"
			+ KEY_title + " TEXT," + 
			KEY_postcontent + " TEXT," + 
			KEY_attachment + " TEXT," + 
			KEY_attachment_type + " TEXT," + 
			KEY_avatar + " TEXT," + 
			KEY_share_count + " TEXT," + 
			KEY_like_count + " TEXT," + 
			KEY_reward_points + " TEXT," + 
			KEY_news_type + " TEXT," + 
			KEY_wp_id + " TEXT," + 
			KEY_sponsorid + " TEXT," + 
			KEY_seen + " TEXT" +

	")";

	String CREATE_CONTACTS_TABLE1 = "CREATE TABLE " + TABLE_CONTACTS1 + "("
			+ KEY_ID+ " INTEGER PRIMARY KEY,"
			+ KEY_posttype + " TEXT,"
			+ KEY_title + " TEXT," + 
			KEY_postcontent + " TEXT," + 
			KEY_attachment + " TEXT," + 
			KEY_attachment_type + " TEXT," + 
			KEY_avatar + " TEXT," + 

	 KEY_reward_points + " TEXT," + 
	 KEY_news_type + " TEXT," + 
	 KEY_wp_id + " TEXT," + 
	 KEY_seen + " TEXT" +


	")";



	String CREATE_CONTACTS_TABLE4 = "CREATE TABLE " + TABLE_CONTACTS4 + "("
			+ KEY_ID+ " INTEGER PRIMARY KEY,"
			+ KEY_posttype + " TEXT,"
			+ KEY_title + " TEXT," + 
			KEY_postcontent + " TEXT," + 
			KEY_attachment + " TEXT," + 
			KEY_attachment_type + " TEXT," + 
			KEY_avatar + " TEXT," + 
			KEY_reward_points + " TEXT," + 
			KEY_news_type + " TEXT," + 
			KEY_wp_id + " TEXT," + 
			KEY_seen + " TEXT" +


	")";




	String CREATE_CONTACTS_TABLE2 = "CREATE TABLE " + TABLE_CONTACTS2 + "("
			+ KEY_ID+ " INTEGER PRIMARY KEY,"
			+ KEY_posttype + " TEXT,"
			+ KEY_title + " TEXT," + 
			KEY_postcontent + " TEXT," + 
			KEY_attachment + " TEXT," + 
			KEY_attachment_type + " TEXT," + 
			KEY_avatar + " TEXT," + 
			KEY_share_count + " TEXT," + 
			KEY_like_count + " TEXT," + 
			KEY_reward_points + " TEXT," + 
			KEY_news_type + " TEXT," + 
			KEY_wp_id + " TEXT," + 
			KEY_sponsorid + " TEXT," + 
			FOLDER_ID + " TEXT" + 


	")";




	String CREATE_CONTACTS_TABLE3 = "CREATE TABLE " + TABLE_CONTACTS3 + "("
			+ KEY_ID+ " INTEGER PRIMARY KEY,"
			+ KEY_posttype + " TEXT,"
			+ KEY_title + " TEXT," + 
			KEY_postcontent + " TEXT," + 
			KEY_attachment + " TEXT," + 
			KEY_attachment_type + " TEXT," + 
			KEY_avatar + " TEXT," + 
			KEY_reward_points + " TEXT," + 
			KEY_news_type + " TEXT," + 
			KEY_wp_id + " TEXT," +
			FOLDER_ID + " TEXT" + 

	")";


	String CREATE_FAVFOLDERS = "CREATE TABLE " + FAVFOLDERS + "("
			+ KEY_ID+ " INTEGER PRIMARY KEY,"
			+ KEY_FOLDER + " TEXT,"+
			KEY_FOLDERTYPE + " TEXT" + 

	")";


	String CREATE_BLOCKFOLDERS = "CREATE TABLE " + BLOCKFOLDERS + "("
			+ KEY_ID+ " INTEGER PRIMARY KEY,"
			+ KEY_FOLDER + " TEXT"+ 

	")";




	String CREATE_WAVE_MARK = "CREATE TABLE " + TABLE_WAVEMARK + "("
			+ KEY_ID+ " INTEGER PRIMARY KEY,"
			+ KEY_description + " TEXT,"
			+ PROJECT_ID + " TEXT,"
			+ KEY_error_code + " TEXT," + 
			KEY_id + " TEXT," + 
			KEY_image_id + " TEXT," + 
			KEY_image_url + " TEXT," + 
			KEY_meta_data + " TEXT," + 
			KEY_status + " TEXT," + 
			KEY_title  + " TEXT," + 
			KEY_url + " TEXT," + 
			EXPIRY_MESSAGE + " TEXT," + 
			PROJECT_START_DATE + " TEXT," +
			PROJECT_END_DATE + " TEXT," +
			PROJECT_START_TIME + " TEXT," +
			PROJECT_END_TIME + " TEXT" +
			")";
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {


			db.execSQL(CREATE_CONTACTS_TABLE);
			db.execSQL(CREATE_CONTACTS_TABLE1);
			db.execSQL(CREATE_BLOCKFOLDERS);
			db.execSQL(CREATE_CONTACTS_TABLE4);
			db.execSQL(CREATE_CONTACTS_TABLE2);
			db.execSQL(CREATE_CONTACTS_TABLE3);
			db.execSQL(CREATE_FAVFOLDERS);
			db.execSQL(CREATE_WAVE_MARK);			

			db.execSQL("insert into favFolders(foldername,foldertype) values ('Create New Folder','1'),('Default Folder 1','1'),('Default Folder 2','1'),('Source','2'),('Author','2'),('Category','2')");
			db.execSQL("insert into blockFolders(foldername) values ('Source'),('Author'),('Category')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed

		// Create tables again

		try {
			if(oldVersion==1){
				Cursor cur = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND (name='wavemark')",null);
				if(cur!=null &&  cur.getCount()==0){
					db.execSQL(CREATE_WAVE_MARK);
				}
				final String ALTER_TBL = "ALTER TABLE " + TABLE_WAVEMARK +
						" ADD COLUMN "+PROJECT_ID+" TEXT";
				db.execSQL(ALTER_TBL);
				
				final String ALTER_TBL1 = "ALTER TABLE " + TABLE_WAVEMARK +
						" ADD COLUMN "+PROJECT_START_DATE+" TEXT";
				db.execSQL(ALTER_TBL1);
				
				final String ALTER_TBL2 = "ALTER TABLE " + TABLE_WAVEMARK +
						" ADD COLUMN "+PROJECT_END_DATE+" TEXT";
				db.execSQL(ALTER_TBL2);
				
				final String ALTER_TBL3 = "ALTER TABLE " + TABLE_WAVEMARK +
						" ADD COLUMN "+PROJECT_START_TIME+" TEXT";
				db.execSQL(ALTER_TBL3);
				
				final String ALTER_TBL4 = "ALTER TABLE " + TABLE_WAVEMARK +
						" ADD COLUMN "+PROJECT_END_TIME+" TEXT";
				db.execSQL(ALTER_TBL4);
				
				final String ALTER_TBL5 = "ALTER TABLE " + TABLE_WAVEMARK +
						" ADD COLUMN "+EXPIRY_MESSAGE+" TEXT";
				db.execSQL(ALTER_TBL5);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	/*public // Adding new contact
	void addContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_posttype, contact.getposttype()); // Contact Name
		values.put(KEY_title, contact.gettitle()); // Contact Phone
		values.put(KEY_postcontent, contact.getpostcontent());
		values.put(KEY_attachment, contact.getattachment());
		values.put(KEY_attachment_type, contact.getattachment_type());
		values.put(KEY_avatar, contact.getavatar());
		values.put(KEY_share_count, contact.getshare_count());
		values.put(KEY_like_count, contact.getlike_count());
		values.put(KEY_reward_points, contact.getreward_points());
		values.put(KEY_news_type, contact.getnews_type());
		values.put(KEY_wp_id, contact.getwp_id());
		values.put(KEY_sponsorid, contact.getsponsorid());

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}*/


/*	public void addContact1(NewsFeed newsfeed) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_posttype, newsfeed.getposttype()); // Contact Name
		values.put(KEY_title, newsfeed.gettitle()); // Contact Phone
		values.put(KEY_postcontent, newsfeed.getpostcontent());
		values.put(KEY_attachment, newsfeed.getattachment());
		values.put(KEY_attachment_type, newsfeed.getattachment_type());
		values.put(KEY_avatar, newsfeed.getavatar());
		values.put(KEY_seen, newsfeed.getSeen());
		values.put(KEY_reward_points, newsfeed.getreward_points());
		values.put(KEY_news_type, newsfeed.getnews_type());
		values.put(KEY_wp_id, newsfeed.getwp_id());


		// Inserting Row
		db.insert(TABLE_CONTACTS1, null, values);
		db.close(); // Closing database connection
	}
*//*
	public void insertCelebFeed(NewsFeed newsfeed) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_posttype, newsfeed.getposttype()); // Contact Name
		values.put(KEY_title, newsfeed.gettitle()); // Contact Phone
		values.put(KEY_postcontent, newsfeed.getpostcontent());
		values.put(KEY_attachment, newsfeed.getattachment());
		values.put(KEY_attachment_type, newsfeed.getattachment_type());
		values.put(KEY_avatar, newsfeed.getavatar());
		values.put(KEY_seen, newsfeed.getSeen());
		values.put(KEY_reward_points, newsfeed.getreward_points());
		values.put(KEY_news_type, newsfeed.getnews_type());
		values.put(KEY_wp_id, newsfeed.getwp_id());


		// Inserting Row
		db.insert(TABLE_CONTACTS4, null, values);
		db.close(); // Closing database connection
	}

	public void addFav(Favseeds contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_title, contact.get_title()); // Contact Phone
		values.put(KEY_wp_id, contact.getWpid());
		values.put(KEY_attachment, contact.getImg());
		values.put(FOLDER_ID, contact.getFolderid());

		db.insert(TABLE_CONTACTS2, null, values);
		db.close(); // Closing database connection
	}


	public void newsFav(Favseeds contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_title, contact.get_title()); // Contact Phone
		values.put(KEY_wp_id, contact.getWpid());
		values.put(KEY_attachment, contact.getImg());
		values.put(KEY_postcontent, contact.getContent());
		values.put(FOLDER_ID, contact.getFolderid());
		db.insert(TABLE_CONTACTS3, null, values);
		db.close(); // Closing database connection
	}


	// Getting All Contacts
	public List<Contact> getAllContacts() {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setposttype(cursor.getString(1));
				contact.settitle(cursor.getString(2));
				contact.setpostcontent(cursor.getString(3));
				contact.setattachment(cursor.getString(4));
				contact.setattachment_type(cursor.getString(5));
				contact.setavatar(cursor.getString(6));
				contact.setshare_count(cursor.getString(7));
				contact.setlike_count(cursor.getString(8));
				contact.setreward_points(cursor.getString(9));
				contact.setnews_type(cursor.getString(10));
				contact.setwp_id(cursor.getString(11));
				contact.setsponsorid(cursor.getString(12));
				contact.setSeen(cursor.getColumnName(cursor.getColumnIndex(KEY_seen)));

				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		if(db!=null)db.close();
		if(cursor!=null)cursor.close();


		// return contact list
		return contactList;
	}


	// Getting All Contacts
	public List<NewsFeed> getAllfeeds() {
		List<NewsFeed> contactList = new ArrayList<NewsFeed>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS1;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				NewsFeed contact = new NewsFeed();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setposttype(cursor.getString(1));
				contact.settitle(cursor.getString(2));
				contact.setpostcontent(cursor.getString(3));
				contact.setattachment(cursor.getString(4));
				contact.setattachment_type(cursor.getString(5));
				contact.setavatar(cursor.getString(6));
				contact.setreward_points(cursor.getString(7));
				contact.setnews_type(cursor.getString(8));
				contact.setwp_id(cursor.getString(9));
				contact.setSeen(cursor.getColumnName(cursor.getColumnIndex(KEY_seen)));

				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		return contactList;
	}

	// Deleting single contact
	public void deleteContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
		db.close();
	}

	public void deleteItem(String id) {

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS2, "wpid =" +id, null);
		Log.e("trghfg", ""+id);

	} 


	public List<Favseeds> getAllFavseeds() {
		List<Favseeds> favseedslistList = new ArrayList<Favseeds>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS3;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Favseeds favseed = new Favseeds();
				favseed.set_id(Integer.parseInt(cursor.getString(0)));

				favseed.set_title(cursor.getString(2));

				favseed.setImg(cursor.getString(4));

				favseed.setWpid(cursor.getString(9));
				favseed.setFolderid(cursor.getColumnName(cursor.getColumnIndex(FOLDER_ID)));

				// Adding contact to list
				favseedslistList.add(favseed);
			} while (cursor.moveToNext());
		}

		// return contact list
		return favseedslistList;
	}

	public List<Favseeds> getAllSponseeds(String feed_id) {
		List<Favseeds> sponsseedslist = new ArrayList<Favseeds>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS2;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Favseeds fpsonseed = new Favseeds();
				fpsonseed.set_id(Integer.parseInt(cursor.getString(0)));
				fpsonseed.setContent(cursor.getString(cursor.getColumnIndex(KEY_postcontent)));
				fpsonseed.set_title(cursor.getString(2));

				fpsonseed.setImg(cursor.getString(4));

				fpsonseed.setWpid(cursor.getString(11));
				fpsonseed.setFolderid(cursor.getString(cursor.getColumnIndex(FOLDER_ID)));

				// Adding contact to list
				sponsseedslist.add(fpsonseed);
			} while (cursor.moveToNext());
		}

		// return contact list
		return sponsseedslist;
	}

	public void removefavlist(Favseeds fav) {
		Log.e("Wpid", fav.getWpid());
		String Query = "select * from newsfav where wpid='"+fav.getWpid()+"'";		
		SQLiteDatabase db2 = getReadableDatabase();
		Cursor cursor1 = db2.rawQuery(Query, null);

		int count1 = cursor1.getCount();
		Log.e("CCCCCC", ":"+count1);

		long j = db2.delete(TABLE_CONTACTS3,"wpid=? ", new String[]{fav.getWpid()});
		if(j>0)
		{
			Log.e("db update", "success");
		}else
		{
			Log.e("unsuccesful", "failure");
		}


	}

	public void removesponseredfavlist(Favseeds fav) {
		Log.e("Wpid", fav.getWpid());
		String Query = "select * from favfeeds where wpid='"+fav.getWpid()+"'";		
		SQLiteDatabase db2 = getReadableDatabase();
		Cursor cursor1 = db2.rawQuery(Query, null);

		int count1 = cursor1.getCount();
		Log.e("CCCCCC", ":"+count1);

		long j = db2.delete(TABLE_CONTACTS2,"wpid=? ", new String[]{fav.getWpid()});
		if(j>0)
		{
			Log.e("db update", "success");
		}else
		{
			Log.e("unsuccesful", "failure");
		}

	}

	public String UpdateSeen(String seen,String wpid,int code) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = KEY_wp_id + " =?";
		ContentValues values = new ContentValues();
		values.put(KEY_seen, seen);
		int i = 0;
		if(code == 1){
			i  = db.update(TABLE_CONTACTS1, values, where, new String[]{wpid});
		}else if(code == 2){

			i  = db.update(TABLE_CONTACTS, values, where, new String[]{wpid});
		}else if(code == 3){

			i  = db.update(TABLE_CONTACTS4, values, where, new String[]{wpid});
		}
		if(i>0)
		{
			System.out.println(" updated media path");
		}else{
			System.out.println(" not updated Media path");
		}
		return seen; 

	}

	public List<String> getAllLabels(){
		List<String> labels = new ArrayList<String>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + FAVFOLDERS + " where foldertype='1'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				labels.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		db.close();

		// returning lables
		return labels;
	}

	public List<String> getAllBlockLabels(){
		List<String> labels = new ArrayList<String>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + FAVFOLDERS + " where foldertype='2'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				labels.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		db.close();

		// returning lables
		return labels;
	}

	public void createFolder(String names, String nums) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_FOLDER,names);
		values.put(KEY_FOLDERTYPE,nums);

		db.insert(FAVFOLDERS, null, values);
		db.close(); // Closing database connection


	}

	public void dbinsertsponsered(String posttype, String title,
			String postcontent, String attachment, String attachment_type, String avatar,
			String share_article, String like_article, String total_feed_points,
			String sponsered, String wp_id, String sponsorid) {

		SQLiteDatabase db2 = getReadableDatabase();
		ContentValues values =  new ContentValues();
		values.put(KEY_posttype, posttype);
		values.put(KEY_title, title);
		values.put(KEY_postcontent, postcontent);
		values.put(KEY_attachment, attachment);
		values.put(KEY_attachment_type, attachment_type);
		values.put(KEY_avatar, avatar);
		values.put(KEY_share_count, share_article);
		values.put(KEY_like_count, like_article);
		values.put(KEY_reward_points, total_feed_points);
		values.put(KEY_news_type, sponsered);
		values.put(KEY_wp_id, wp_id);
		values.put(KEY_sponsorid, sponsorid);
		long i = db2.insert(TABLE_CONTACTS, null, values);
		if(i>0)
		{
			Log.e("No error", "Error");
		}else
		{
			Log.e("Error", "Error");
		}



	}

	public void dbinsert(String posttype, String title, String postcontent,
			String attachment, String attachment_type, String wp_id,String ngagelogo,String news_type) {
		SQLiteDatabase db2 = getReadableDatabase();
		ContentValues values =  new ContentValues();
		values.put(KEY_posttype, posttype);
		values.put(KEY_title, title);
		values.put(KEY_postcontent, postcontent);
		values.put(KEY_attachment, attachment);
		values.put(KEY_attachment_type, attachment_type);
		values.put(KEY_wp_id, wp_id);
		values.put(KEY_avatar, ngagelogo);
		values.put(KEY_news_type, news_type);
		long i = db2.insert(TABLE_CONTACTS1, null, values);
		if(i>0)
		{
			Log.e("No error", "Error");
		}else
		{
			Log.e("Error", "Error");
		}


	}
*/	
	public void insert_wavemark_data(String description, String string, String string2, String image_id, String image_url, String meta, 
			String status, String title, String url,String Project_id,String start_date,String end_date,String start_time,String end_time,String expiry_message) {
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "select * from wavemark where idunique = '"+string2+"'";
		Cursor cursor = db.rawQuery(query, null);
		if(cursor!=null && cursor.getCount()==0)
		{//String description, int error, long id, String image_id, String image_url, 
			//String meta, String status, String title, String url) {

			ContentValues values= new ContentValues();
			values.put(KEY_description, description);
			values.put(KEY_error_code, string);
			values.put(KEY_id, string2);
			values.put(KEY_image_id, image_id);
			values.put(KEY_image_url, image_url);
			values.put(KEY_meta_data, meta);
			values.put(KEY_status, status);
			values.put(KEY_title, title);
			values.put(KEY_url, url);
			values.put(PROJECT_ID, Project_id);
			values.put(PROJECT_START_DATE, start_date);
			values.put(PROJECT_END_DATE, end_date);
			values.put(PROJECT_START_TIME, start_time);
			values.put(PROJECT_END_TIME, end_time);
			values.put(EXPIRY_MESSAGE, expiry_message);
			
			
			long i = db.insert(TABLE_WAVEMARK, null, values);
			if(i>0){
				Log.e("sucess", "Sucess");
			}else
			{
				Log.e("Error", "Error");
			}
			//			db.close();

		}

	}

	public List<ResponseModal> get_all_wavemark(){
		SQLiteDatabase db = this.getReadableDatabase();
	
		List<ResponseModal> ls = new ArrayList<ResponseModal>();;
		Cursor cursor = db.rawQuery("select * from wavemark ", null);
		
		if (cursor!=null && cursor.getCount() > 0) {
			System.out.println(" cursor1 is not null 1");
			if (!cursor.isAfterLast()) {
				System.out.println(" cursor1 is not null 2");
				cursor.moveToFirst();

				while(!cursor.isAfterLast()){
					 	ResponseModal obj = new ResponseModal();
					String imageurl = cursor.getString(cursor.getColumnIndex(KEY_image_url));
					String title = cursor.getString(cursor.getColumnIndex(KEY_title));
					String description = cursor.getString(cursor.getColumnIndex(KEY_description));
					String url = cursor.getString(cursor.getColumnIndex(KEY_url));
					long id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_id)));
					String start_date = cursor.getString(cursor.getColumnIndex(PROJECT_START_DATE));
					String end_date = cursor.getString(cursor.getColumnIndex(PROJECT_END_DATE));
					String expiry_message = cursor.getString(cursor.getColumnIndex(EXPIRY_MESSAGE));
					obj.setDescription(description);
					obj.setImageUrl(imageurl);
					obj.setTitle(title);
					obj.setUrl(url);
					obj.setId(id);
					obj.setStart_Date(start_date);
					obj.setEnd_Date(end_date);
					obj.setExpiry_message(expiry_message);
					
					ls.add(obj);
					cursor.moveToNext();
				}
			}
		}
		return ls;
	}

}
