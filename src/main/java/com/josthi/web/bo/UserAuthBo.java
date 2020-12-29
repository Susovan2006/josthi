package com.josthi.web.bo ;
import java.sql.Timestamp ;
import java.util.List;



public class UserAuthBo {
	private String customerId ;
	private String useridEmail ;
	private String wordapp ;
	private Timestamp registrationDateTime ;
	private String registrationDateTimeStr ;
	private String status ;
	private Timestamp loginTime ;
	private String loginStatus ;
	private String securityQuestion1 ;
	private String securityQuestion2 ;
	private String securityAnswer1 ;
	private String securityAnswer2 ;
	private String role ;
	private String comments ;
	private int loginRetryCount;
	private String temporaryLockEnabled;
	private String verifiedUser;
	private String otp;
	private String validEmail;
	

	
	public String getCustomerId(){
		return customerId ;
	}
	public void setCustomerId(String customerId){
		this.customerId = customerId ;
	}
	public String getUseridEmail(){
		return useridEmail ;
	}
	public void setUseridEmail(String useridEmail){
		this.useridEmail = useridEmail ;
	}
	public String getWordapp(){
		return wordapp ;
	}
	public void setWordapp(String wordapp){
		this.wordapp = wordapp ;
	}
	public Timestamp getRegistrationDateTime(){
		return registrationDateTime ;
	}
	public void setRegistrationDateTime(Timestamp registrationDateTime){
		this.registrationDateTime = registrationDateTime ;
	}
	
	
	public String getRegistrationDateTimeStr() {
		return registrationDateTimeStr;
	}
	public void setRegistrationDateTimeStr(String registrationDateTimeStr) {
		this.registrationDateTimeStr = registrationDateTimeStr;
	}
	public String getStatus(){
		return status ;
	}
	public void setStatus(String status){
		this.status = status ;
	}
	public Timestamp getLoginTime(){
		return loginTime ;
	}
	public void setLoginTime(Timestamp loginTime){
		this.loginTime = loginTime ;
	}
	public String getLoginStatus(){
		return loginStatus ;
	}
	public void setLoginStatus(String loginStatus){
		this.loginStatus = loginStatus ;
	}
	public String getSecurityQuestion1(){
		return securityQuestion1 ;
	}
	public void setSecurityQuestion1(String securityQuestion1){
		this.securityQuestion1 = securityQuestion1 ;
	}
	public String getSecurityQuestion2(){
		return securityQuestion2 ;
	}
	public void setSecurityQuestion2(String securityQuestion2){
		this.securityQuestion2 = securityQuestion2 ;
	}
	public String getSecurityAnswer1(){
		return securityAnswer1 ;
	}
	public void setSecurityAnswer1(String securityAnswer1){
		this.securityAnswer1 = securityAnswer1 ;
	}
	public String getSecurityAnswer2(){
		return securityAnswer2 ;
	}
	public void setSecurityAnswer2(String securityAnswer2){
		this.securityAnswer2 = securityAnswer2 ;
	}
	public String getRole(){
		return role ;
	}
	public void setRole(String role){
		this.role = role ;
	}
	public String getComments(){
		return comments ;
	}
	public void setComments(String comments){
		this.comments = comments ;
	}
	
	public int getLoginRetryCount() {
		return loginRetryCount;
	}
	public void setLoginRetryCount(int loginRetryCount) {
		this.loginRetryCount = loginRetryCount;
	}
	public String getTemporaryLockEnabled() {
		return temporaryLockEnabled;
	}
	public void setTemporaryLockEnabled(String temporaryLockEnabled) {
		this.temporaryLockEnabled = temporaryLockEnabled;
	}
	
	public String getVerifiedUser() {
		return verifiedUser;
	}
	public void setVerifiedUser(String verifiedUser) {
		this.verifiedUser = verifiedUser;
	}
	
	
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	
	public String getValidEmail() {
		return validEmail;
	}
	public void setValidEmail(String validEmail) {
		this.validEmail = validEmail;
	}
	@Override
	public String toString() {
		return "UserAuthBo [customerId=" + customerId + ", useridEmail=" + useridEmail + ", wordapp=" + wordapp
				+ ", registrationDateTime=" + registrationDateTime + ", registrationDateTimeStr="
				+ registrationDateTimeStr + ", status=" + status + ", loginTime=" + loginTime + ", loginStatus="
				+ loginStatus + ", securityQuestion1=" + securityQuestion1 + ", securityQuestion2=" + securityQuestion2
				+ ", securityAnswer1=" + securityAnswer1 + ", securityAnswer2=" + securityAnswer2 + ", role=" + role
				+ ", comments=" + comments + ", loginRetryCount=" + loginRetryCount + ", temporaryLockEnabled="
				+ temporaryLockEnabled + ", verifiedUser=" + verifiedUser + ", otp=" + otp + "]";
	}
	
	
	
	
	
	
}
