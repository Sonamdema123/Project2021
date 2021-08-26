package edu.gcit.tshogyen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Candidates {

    Integer CandidateVote;
    String id, FullName, CandidateID, CandidateEmail, CandidateRole, Image;

    static final String NAME_KEY = "FullName";

    public Candidates(String id, String FullName, String CandidateEmail, String CandidateID, String CandidateRole, String Image, int CandidateVote) {
        this.id = id;
        this.FullName = FullName;
        this.CandidateEmail = CandidateEmail;
        this.CandidateID = CandidateID;
        this.CandidateRole = CandidateRole;
        this.Image = Image;
        this.CandidateVote = CandidateVote;
    }

    public int getCandidateVote(){
        return CandidateVote;
    }

    public void setCandidateVote(int CandidateVote) {
        this.CandidateVote = CandidateVote;
    }

    public String getImage(){
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        this.FullName = fullName;
    }

    public String getCandidateID() {
        return CandidateID;
    }

    public void setCandidateID(String candidateID) {
        this.CandidateID = candidateID;
    }

    public String getCandidateEmail() {
        return CandidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.CandidateEmail = candidateEmail;
    }

    public String getCandidateRole() {
        return CandidateRole;
    }

    public void setCandidateRole(String candidateRole) {
        this.CandidateRole = candidateRole;
    }

    public static Intent starter(Context viewCandidateActivity, String FullName) {
        Intent detailIntent = new Intent(viewCandidateActivity, ManifestActivity.class);
        detailIntent.putExtra(NAME_KEY, FullName);
        return detailIntent;
    }
}
