package com.FarmPe.Farms;


  public class Urls {

   private static final String ROOT_URL = "http://13.232.185.209:909/api/";///DEV
  // private static final String ROOT_URL = "http://3.17.6.57:9393/api/";///DEV
   // private static final String ROOT_URL = "http://3.17.6.57:8888//api/";//PRO

    public static final String IMAGE_ROOT_URL = "http://13.232.185.209:909";//Image root
    public static final String Add_New_Address = ROOT_URL+"MasterTable/AddUserAddress";
    public static final String GetFarmerDetailsList = ROOT_URL+"MasterTable/GetFarmersList";

    public static final String LOGIN=ROOT_URL+"Auth/ValidateUser";
    public static final String SIGNUP=ROOT_URL+"Auth/RegisterUser";
    public static final String GetAllCrops=ROOT_URL+"Crops/GetCrops";
    public static final String AddToCart=ROOT_URL+"Order/AddToCart";
    public static final String Languages=ROOT_URL+"MasterTable/GetLanguages";
    public static final String Forgot_Password=ROOT_URL+"Auth/ForgotPassword";
    public static final String ChangePassword=ROOT_URL+"Auth/ChangePassword";
    public static final String ResendOTP=ROOT_URL +"Auth/ResendOTP";
    public static final String VerifyOTPNewUser=ROOT_URL+"Auth/VerifyOTPNewUser";
    public static final String GetUserDetails=ROOT_URL+"GetUserDetails";
    public static final String ValidateReferalCode=ROOT_URL+"ValidateRefferalCode";
    public static final String CHANGE_LANGUAGE= ROOT_URL+"Lang/ChangeCurrentCulture";



    public static final String Get_New_Address = ROOT_URL+"MasterTable/GetUserAddress";
    // Wallet
    public static final String GetFarmDetailsList = ROOT_URL+"MasterTable/GetFarmsList";
    public static final String GetFarmsListByUserId = "http://13.232.185.209:909/api/MasterTable/GetFarmsListByUserId";


    // Refer n Earn
    public static final String Refferal_Code = ROOT_URL +"Auth/GetUserDetails";


    //Wallet balance
    public static final String GetWalletDetails = ROOT_URL +"MasterTable/GetWalletDetails";


    // Address
    public static final String Delete_Address_Details = ROOT_URL + "MasterTable/DeleteUserAddress";
    public static final String Default_Address = ROOT_URL + "MasterTable/UpdateUserDefaultAddress";
    public static final String Edit_Address = ROOT_URL + "MasterTable/UpdateUserDefaultAddress";


   //feedback
   public static final String AddFeedback = ROOT_URL + "MasterTable/AddFeedback";


     //profile details
    public static final String Get_Profile_Details= ROOT_URL + "Auth/GetUserDetails";
    public static final String Update_Profile_Details= ROOT_URL + "Auth/UpdateUserProfile";


   //Notification
   public static final String GET_NOTIFICATION= ROOT_URL + "MasterTable/GetNotificationMaster";
   public static final String GET_NOTIFICATIONLIST= ROOT_URL + "MasterTable/GetNotifications";
   public static final String UPDATEUSERNOTIFICATIONSETTING= ROOT_URL + "Auth/UpdateUserNotificationSettings";

   // http://3.17.6.57:8686/api/Auth/UpdateUserNotificationSettings

    public static final String Districts=ROOT_URL+"MasterTable/GetDistricts";
    public static final String Taluks=ROOT_URL+"MasterTable/GetTaluks";
    public static final String Hoblis=ROOT_URL+"MasterTable/GetHoblis";
    public static final String Villages=ROOT_URL+"MasterTable/GetVillages";
    public static final String State = ROOT_URL+"MasterTable/GetStates";


    public static final String GetBrandList = ROOT_URL+"MasterTable/GetBrandList";
    public static final String ModelList = ROOT_URL+"MasterTable/GetModels";
    public static final String GetHPList = ROOT_URL+"MasterTable/GetHPList";
    public static final String AddRequestForQuotation = ROOT_URL+"MasterTable/AddUpdateRequestForQuotation";
    public static final String GetLookingForItems = ROOT_URL+"MasterTable/GetLookingForDetails";
    public static final String GetLookingForFirst = ROOT_URL+"MasterTable/GetLookingFor";
    public static final String GetLookingForList = ROOT_URL+"MasterTable/GetLookingForLists";
    public static final String YourRequest = ROOT_URL+"MasterTable/GetLookingForListsById";


    //List Your Farms

    public static final String List_Your_Farms = ROOT_URL+"MasterTable/GetFarmCategoryList";
    public static final String Farm_Type_List = ROOT_URL+"MasterTable/GetFarmTypesList";
    public static final String Farm_Details = ROOT_URL+"MasterTable/AddUpdateFarms";
    public static final String AddUpdateFarms = ROOT_URL+"MasterTable/AddUpdateFarms";
    public static final String Invitation_Farm = ROOT_URL+"MasterTable/GetInvitationList";
    public static final String Invitn_accpt_cancel = ROOT_URL+"MasterTable/RespondToConnectionRequest";




    //Request Quoatation
    public static final String Get_Edit_Request = ROOT_URL + "MasterTable/GetLookingForListsById";
    public static final String Delete_Request = ROOT_URL + "MasterTable/DeleteRequestForQuotation";


  //Connections

   public static final String Get_Connection_List = ROOT_URL + "MasterTable/GetConnectionList";

   //Homepage_Count
   public static final String Home_Page_Count = ROOT_URL + "MasterTable/GetCountForFarmer";

 //Notification

   public static final String Notification_HomePage = ROOT_URL + "MasterTable/GetNotifications";

}
