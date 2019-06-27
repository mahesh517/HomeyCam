package com.app.homeycam.Utils;



public interface AppConstants {

    String DEVICE_TYPE = "android";

    int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    long SPLASH_SCREEN_TIMEOUT = 500;

    int STATUS_CODE_SESSION_EXPIRED = 401;

    int REQUEST_CODE_SELECT_CONTACT = 45;

    String SUCCESS = "success";
    String CRON_ADMIN = "CRON_ADMIN";
    String AM = "AM";
    String PM = "PM";
    String FRAGMENT_MY_RATIB = "my_ratib";
    String ACTIVITY_NAME = "activity_name";
    String KEY_SERIALIZABLE = "key_serializable";
    String MONTH_YEAR_FORMATTER = "MMMM yyyy";
    String ACTIVITY_LOGIN = "login";
    String ACTIVITY_MYACCOUNT = "myACCOUNT";
    String OTP_SENDER_NAME = "RATIBP";
    int FALSE = 0;
    int TRUE = 1;
    int REQUEST_CODE_LOCATION = 99;
    String DATE_FORMATTER = "dd MMM yyyy";
    String ACTIVITY_CLEAR_DUES = "clear_dues";
    String ACTIVITY_PAYMENT_HISTORY = "payment_history";
    int SELECT_START_DATE = 45;

    int SELECT_END_DATE = 47;

    int SELECT_END_TIME = 47;

    int SELECT_START_TIME = 45;

    int SUNDAY = 0;

    int MONDAY = 1;

    int TUESDAY = 2;

    int WEDNESDAY = 3;

    int THURSDAY = 4;

    int FRIDAY = 5;

    int SATURDAY = 6;



    String TYPE_GEO_LOCATION = "Point";
    String APP_TYPE_SUPPLIER = "supplier";

    String MOBILE_NO = "mobileNo";
    String SELECTED_LATITUDE = "selected_latitude";
    String SELECTED_LONGITUDE = "selected_longitude";
    String ADDRESS = "address";
    String OLD_ADDRESS = "oldAddress";
    String FLATNAME = "flat_name";
    String LOCTYPEDC = "DC";
    String LOCTYPEDA = "DA";

    String ADD = "add";

    String CUSTOMER_PAID = "CUSTOMER_PAID";
    String SHOP_NO = "shopNo";
    String FOR_CASH = "Cash";
    String FOR_CHEQUE = "Cheque";
    String SMS_BODY = "sms_body";
    String SMS_TYPE = "vnd.android-dir/mms-sms";
    String LOCATION_NAME = "locationName";

    String EDIT = "edit";

    String DELETE = "delete";
    String CITY = "city";
    String STATE = "state";
    String ZIPCODE = "zipcode";
    String RADIUS = "radius";
    String RADIUS_EDIT = "radius_edit";
    String LOCALITY = "locality";
    String LOCATION_TYPE = "location_type";

    String FROM_ADD = "fromAdd";

    String FROM_EDIT = "fromEdit";

    String NEWSPAPER_MODEL = "newspaperModel";
    String SERVICE_MODEL = "serviceModel";
    String MILK_MODEL = "milkModel";
    String JUICE_MODEL = "juiceModel";

    String AREA = "area";

    String STATUS = "status";
    String FROM_BUSINESS_DETAILS = "fromBusinessDetails";
    String STATUS_INSERT = "insert";

    String STATUS_UPDATE = "update";
    String STATUS_SYNCED = "synced";
    String STATUS_DELETE = "delete";

//    String CHEQUE_IMAGE_THUMB_URL = BuildConfig.BASE_URL + "api/v1/file/download/SupplierBankProofImages/FID?isThumbnail=true";
//
//    String PROFILE_IMAGE_THUMB_URL = BuildConfig.BASE_URL + "api/v1/file/download/ProfileImages/FID?isThumbnail=true";
//
//    String AADHAAR_IMAGE_THUMB_URL = BuildConfig.BASE_URL + "api/v1/file/download/AadhaarImages/FID?isThumbnail=true";


    String SUPPLIER_ROLE = "S";
    String SUPPLIER_ROLE_ = "Supplier";
    String LAST_SYNCED = "lastSynced";

    String CONSUMER_ROLE = "C";
    String FROM = "from";
    String URL = "url";
    String URL_GOOGLE = "http://www.google.com";

    String FROM_ACTIVITY = "fromActivity";

    String FROM_LOGIN_ACTIVITY = "fromLoginActivity";
    String AGREE = "agree";
    String I_AGREE = "iAgree";
    String LANG_CODE_ENGLISH = "en_US";
    String LANG_CODE_HINDI = "hi_IN";

    String USER_SUPPLIERS = "supplier";

    int RECORD_OFFSET = 0;
    String USER_ID = "userId";
    String OFFSET = "offset";
    String LIMIT = "limit";
    String FOR_APP_USER = "forUser";
    String FOR_YEAR_MONTH = "yearAndMonth";
    String FOR_OFFERING_NAME = "searchText";
    String FOR_CATID = "catId";

    String FOR_USER = "forUser";

    String CALENDAR_STATUS_DELIVERED = "D";

    String CALENDAR_STATUS_NOT_DELIVERED = "ND";

    String CALENDAR_STATUS_RATIB_NOT_SET = "RTN";

    String CALENDAR_STATUS_RATIB_FOR_FUTURE = "RFF";

    String CALENDAR_STATUS_UNPLANNED_CANCELLED_BY_SUPPLIER = "CBS";

    String CALENDAR_STATUS_PLANNED_CANCELLED_BY_CUSTOMER = "PCBC";

    String CALENDAR_STATUS_PLANNED_CANCELLED_BY_SUPPLIER = "PCBS";

    String CALENDAR_STATUS_QUANTITY_CHANGED = "QC";

    String CALENDAR_STATUS_QUANTITY_INCREMENTED = "QCI";

    String CALENDAR_STATUS_QUANTITY_DECREMENTED = "QCD";

    String CALENDAR_STATUS_QUANTITY_INCREMENTED_PREVOIUS = "QCIP";

    String CALENDAR_STATUS_QUANTITY_DECREMENTED_PREVOIUS = "QCDP";

    String CATEGORY_ICON_URL = "http://35.165.206.219:8080/" + "api/v1/file/download/IconImages/%1$s?isThumbnail=%2$s";


    String CATEGORY_MILK = "1";

    String CATEGORY_IMAGE_URL = "http://35.165.206.219:8080/" + "api/v1/file/download/CategoryImages/%1$s?isThumbnail=%2$s";


    String CATEGORY_NEWSPAPER = "2";

    String CATEGORY_SERVICE = "4";
    String CATEGORY_SERVICE_CLEANING_MAID = "5";
    String CATEGORY_SERVICE_CAR_WASH = "6";
    String CATEGORY_WATER_BOTTLE = "7";

    String CATEGORY_JUICE = "3";

    String ACTIVITY_INVITE_CUSTOMER = "activity_invite";

    String INVOICE_STATUS_APPROVED = "APPROVED";
    String INVOICE_STATUS_SETTLE = "SETTLE";
    String INVOICE_STATUS_SETTLED = "SETTLE";
    String INVOICE_STATUS_PENDING = "PENDING";
    String INVOICE_STATUS_PAID_BY_CONSUMER = "CUSTOMER_PAID";
    String INVOICE_DUE = "SUPPLIER_DUE";
    String INVOICE_ADVANCE = "ADVANCE";
    String KEY_CATEGORY_ID = "category_id";
    String KEY_CATEGORY_NAME = "category_name";


    String GOOGLE_PAY = "online_g_pay";
    String PAYTM_PAY = "online_paytm";
    String BHIM_PAY = "online_bhim_upi";
    String CASH_PAY = "Cash";

    String TO_HOME = "to_home";
    String TO_PERSONAL_DETAILS = "to_personal_details";
    String TO_DELIVERY_LOCAIONS = "to_delivery_locaions";
    String TO_AADHAR_CARD = "to_aadhar_card";

    String CUSTOMER_RATIB_MODEL = "customer_ratib_model_new";
    String CONSUMER_MODEL = "consumer_model";

    String SELECTED_DATE = "SelectedDate";
    String PDUS = "pdus";
    String SELECTED_ID = "selectedId";
    String ALL_SELECTED = "all_selectedId";
    int REQUEST_CODE_REFRESH_RATIB = 673;
    int REQUEST_CODE_CANCEL_ALL_REFRESH_RATIB = 674;

    String YRNDMONTH = "yrNdMonth";
    String RATIB_STATUS = "ratib_status";
    String ISTODAYSDATE = "isTodaysDate";
    String AMOUNT = "amount";

    String DETAIL = "suppliersDetailModel";
    String MOBILE_EXIST_ERROR = "user.error.mobileNoAlreadyUseByCust";

    String SERVER_UNREACHABLE = "server_unreachable";
    String SMS_RECEIVER_INTENT = "android.provider.Telephony.SMS_RECEIVED";

    String SELECTED_CONSUMER_LIST = "selected_consumer_list";
    String SELECTED_OFFERING_ID = "selected_offering_id";
    String INVITE_VIEW = "invite_view";

    String NOTIFICATION_SUPPLIER_UPDTAED_RATIB_STATUS = "myRatib";

    String NOTIFICATION_SUPPLIER_ADDED_NEW_OFFERING = "addSupplier";

    String NOTIFICATION_MERGE_CUSTOMER = "merge_customer";

    String NOTIFICATION_INVOICE_GENERATED = "invoice_generated";

    String NOTIFICATION_PAYMENT_DUE = "payment_due";

    String NOTIFICATION_SUPPLIER_CHANGES_EXTRAS_DEDUCTIONS = "changes_extras_deductions";

    String NOTIFICATION_SUPPLIER_INVITES_CUSTOMER = "invite";
    String NETWORK_NOT_AVAILABLE = "Internet connection not available. Make sure Wi-fi or cellular data is turned on, then try again";

    String NOTIFICATION_NEW_CONSUMER_REGISTERED = "addConsumer";

    String NOTIFICATION_CONSUMER_MY_RATIB = "myRatib";

    String NOTIFICATION_REGISTRATION_CONTACT_CENTER = "registration_contact_center";

    String NOTIFICATION_CONSUMER_MADE_PAYMENT = "consumer_made_payment";

    String NOTIFICATION_MONTHLY_INVOICE = "monthly_invoice";

    String NOTIFICATION_FORWARDED_RATIB = "forwarded_ratib";

    String NOTIFICATION_TYPE_FORWARDED_RATIB = "forwardedRatib";

    String PERSONAL_DETAILS = "personalDetailsActivity";

    String NOTIFICATION_CONSUMER_CHANGES_EXTRAS_DEDUCTIONS = "changes_extras_deductions";

    String CALL_SUPPORT_TEXT = "or  Call Support";
    String CONTACT = "contact";
    String TNC_EN = "TermsConditionsSupplier-EN";
    String TNC_HI = "TermsConditionsSupplier-HI";
    String ABOUT_EN = "AboutUs-EN";
    String ABOUT_HI = "AboutUs-HI";
    String CONTACT_EN = "ContactUs-EN";
    String CONTACT_HI = "ContactUs-HI";
    String HELP_S_EN = "HelpSupplier-EN";
    String HELP_S_HI = "HelpSupplier-HI";
    String HELP_C_EN = "HelpCustomer-EN";
    String HELP_C_HI = "HelpCustomer-HI";
    String CALL_SUPPORT_CONTACT_NO = "contact_center_no_1";
    String BASE_URL_CATEGORY_ICON = "IconImgUrl";
    String BASE_URL_BANK_IMAGES = "SuppBankImgUrl";
    String BASE_URL_CATEGORY_IMAGES = "CategoryImgUrl";
    String BASE_URL_AADHAR_IMAGES = "AadhaarImgUrl";
    String BASE_URL_PROFILE_IMAGES = "ProfileImgUrl";
    String APP_CONFIG_SYNC_TIME = "supplierAppSyncTimeInSeconds";
    int APP_CONFIG_SYNC_SERVICE_TIME = 30 * 60;

    String MONTH = "month";
    String YEAR = "year";

    String INVOICE_SALARY_CUT = "SALARY_CUT";
    String INVOICE_SALARY_EXTRA = "SALARY_EXTRA";
    String INVOICE_SALARY_ANNUAL_BONUS = "ANNUAL_BONUS";

    String SEND_SMS_CUSTOMER_KEY = "InviteCustomerSMSBody";

    String REGULAR_RATIB = "REGULAR_RATIB";
    String DELIVERY_CHARGE = "DELIVERY_CHARGE";
    String DEDUCTION = "DEDUCTION";
    String EXTRA = "EXTRA";
    String CURRENTMONTH = "CURRENT_MONTH";
    String HI_IN = "hi_IN";
    String SELECT_FILE = "Select File";
    String MIMETYPE_IMAGE_PDF = "image/* | application/pdf";

    String KEY_MOBILE = "mobile";
    String KEY_SEARCH_MOBILE = "mNo";
    String ALERT = "Alert";

    String USER_INVALID = "user.error.invalid";
    String MOBILE_NO_EMPTY = "user.error.mobileNoEmpty";

    int MY_CALENDAR_RECORD_LIMIT = 500;

    int MY_OFFERING_RECORD_LIMIT = 100;

    int MY_RATIB_RECORD_LIMIT = 400;

    int MY_CONSUMER_RECORD_LIMIT = 300;

    int MY_INVOICE_RECORD_LIMIT = 400;

    int RECORD_LIMIT_VALUE = 100;

    String MAILTO = "mailto:";
    String TEL = "tel:";

    String KEY_APP_TYPE = "appType";

    String KEY_DEVICE_TYPE = "deviceType";

    String KEY_APP_VERSION = "appVer";

    String LANGUAGE_CODE_HINDI = "hi";

    String IMAGE_JPEG = "image/jpeg";
    String IMAGE_BMP = "image/bmp";
    String IMAGE_GIF = "image/gif";
    String IMAGE_JPG = "image/jpg";
    String IMAGE_PNG = "image/png";
    String APPLICATION_PDF = "application/pdf";

    String ADDED_VARIENTS = "added";

    String UPDATED_VARIENTS = "updated";
    String PRICING_TYPE_DAILY = "DLY";
    String PRICING_TYPE_MONTHLY = "MTY";
    String PRICING_TYPE_YEARLY = "YRY";
    String ENGLISH_LANGUAUGE_CODE = "en_US";
    String HINDI_LANGUAUGE_CODE = "hi_IN";
    int REQUEST_CHECK_SETTINGS = 564;

    String KEY_NOTIFICATION_TYPE = "notificationType";

    String CUSTOMER_INVOICE_SHOW = "SHOW";
    String CUSTOMER_INVOICE_HIDE = "HIDE";
    String INVOICE_SETTING_UPDATE_STATUS_CODE = "custSuppSetting.success";
    String CUSTOMER_ID = "CUSTOMER_ID";
    String YEAR_N_MONTH = "YEAR_N_MONTH";

    String NULL_STRING = "";

    String NO_NEW_RELEASE = "NO_NEW_RELEASE";

    String MANDATORY_RELEASE = "MANDATORY_RELEASE";

    String UNSUPPORTED_RELEASE = "UNSUPPORTED_RELEASE";

    String OPTIONAL_RELEASE = "OPTIONAL_RELEASE";


    int CUSTOMER_DETAIL_REQUEST_CODE = 2020;
    int DELIVERY_LINE_DRAG_REQUEST_CODE = 3022;

}

