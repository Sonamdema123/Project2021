package com.example.lokhorchunye;

public class MewaModel {

    public String getTitle() { return Title; }
    public void setTitle(String title) { Title = title; }

    public String getAbout() { return About; }
    public void setAbout(String about) { About = about; }

    public String getHealth() { return Health; }
    public void setHealth(String health) { Health = health; }

    public String getLove() { return Love; }
    public void setLove(String love) { Love = love; }

    public String getFortune() { return Fortune; }
    public void setFortune(String fortune) { Fortune = fortune; }

    public String getOccupation() { return Occupation; }
    public void setOccupation(String occupation) { Occupation = occupation; }

    public String getLives() { return Lives; }
    public void setLives(String lives) { Lives = lives; }

    public String getMantra() { return Mantra; }
    public void setMantra(String mantra) { Mantra = mantra; }

    public String getPurification() { return Purification; }
    public void setPurification(String purification) { Purification = purification; }

    public String getId() { return Id; }
    public void setId(String id) { Id = id; }

    //int Id;
    public String Title,About,Health,Love,Fortune,Occupation,Lives,Mantra,Purification,Id;

    public MewaModel(){ }

    public MewaModel(String Id, String Title, String About, String Health, String Love, String Fortune, String Occupation, String Lives, String Purification, String Mantra ){
        this.Id = Id;
        this.Title = Title;
        this.About = About;
        this.Health = Health;
        this.Love = Love;
        this.Fortune = Fortune;
        this.Occupation = Occupation;
        this.Purification = Purification;
        this.Lives = Lives;
        this.Mantra = Mantra;
    }
}
