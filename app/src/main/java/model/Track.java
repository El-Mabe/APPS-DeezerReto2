package model;

public class Track {

    private String name_track;
    private String artist_name;
    private String release_year;
    private String album;
    private String duration_track;

    public Track(){

    }

    public Track(String name_track, String artist_name, String release_year, String album, String duration_track) {
        this.name_track = name_track;
        this.artist_name = artist_name;
        this.release_year = release_year;
        this.album = album;
        this.duration_track = duration_track;
    }

    public String getName_track() {
        return name_track;
    }

    public void setName_track(String name_track) {
        this.name_track = name_track;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getRelease_year() {
        return release_year;
    }

    public void setRelease_year(String release_year) {
        this.release_year = release_year;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration_track() {
        return duration_track;
    }

    public void setDuration_track(String duration_track) {
        this.duration_track = duration_track;
    }
}
