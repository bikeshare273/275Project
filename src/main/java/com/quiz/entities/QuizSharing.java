package com.quiz.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="QuizSharing")
public class QuizSharing 
{

	/*CREATE TABLE QuizSharing(

			quizsharingpid	INTEGER(10),
			userid			INTEGER(10),
			recommenderid	INTEGER(10),
			completedflag	BOOLEAN,

			PRIMARY KEY(quizsharingpid));
*/
	/**********************************************************/
	
		private Integer quizsharingpid;
		private Integer userid;
		private Integer recommenderid;
		private Boolean completedflag;
	
	
	
	/**********************************************************/

		@Id
		@Column(name = "quizsharingpid", unique = true, nullable= false)
		public Integer getQuizsharingpid() 
		{
			return quizsharingpid;
		}
		public void setQuizsharingpid(Integer quizsharingpid) {
			this.quizsharingpid = quizsharingpid;
		}
		
		@ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST)
		@JoinColumn(name = "userid", referencedColumnName = "userid")
		public Integer getUserid() {
			return userid;
		}
		public void setUserid(Integer userid) {
			this.userid = userid;
		}
		
		@Column(name = "recommenderid", unique = true, nullable= true)
		public Integer getRecommenderid() {
			return recommenderid;
		}
		public void setRecommenderid(Integer recommenderid) {
			this.recommenderid = recommenderid;
		}
		
		@Column(name = "completedflag", unique = true, nullable= true)
		public Boolean getCompletedflag() {
			return completedflag;
		}
		public void setCompletedflag(Boolean completedflag) {
			this.completedflag = completedflag;
		}
}
