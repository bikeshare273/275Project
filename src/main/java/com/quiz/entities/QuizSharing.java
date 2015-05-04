package com.quiz.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	
		private Integer quizsharingid;
		private Integer userid;
		private Integer quizid;
		private boolean completedflag;
		private Integer recommenderid;
	
	/**********************************************************/

		@Id
		@Column(name = "quizsharingid", unique = true, nullable= false)
		public Integer getQuizsharingid() 
		{
			return quizsharingid;
		}
		public void setQuizsharingid(Integer quizsharingid) {
			this.quizsharingid = quizsharingid;
		}
		
		@Column(name = "userid", unique = false, nullable= false)
		public Integer getUserid() {
			return userid;
		}
		public void setUserid(Integer userid) {
			this.userid = userid;
		}
		
		@Column(name = "recommenderid", unique = false, nullable= false)
		public Integer getRecommenderid() {
			return recommenderid;
		}
		public void setRecommenderid(Integer recommenderid) {
			this.recommenderid = recommenderid;
		}
		
		
		@Column(name = "completedflag", unique = false, nullable= false)
		public boolean isCompletedflag() {
			return completedflag;
		}
		
		public void setCompletedflag(boolean completedflag) {
			this.completedflag = completedflag;
		}
		
		
		@Column(name = "quizid", unique = false, nullable= false)
		public Integer getQuizid() {
			return quizid;
		}
		public void setQuizid(Integer quizid) {
			this.quizid = quizid;
		}
}
