package com.shazam;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class MatchService {

    /*
     * @param song Current song.
     * @param numTopRatedSimilarSongs the maximum number of song matches to return
     * @return List of top rated similar songs
     */
    static List<Song> getSongMatches(Song song, int numTopRatedSimilarSongs) {
        List<Song> songsInNetwork = getSongsInNetwork(song, new ArrayList<>()).stream().filter(x -> x != song).collect(Collectors.toList());
        List<Song> sortedSongs = getSortedSongs(songsInNetwork);
        return getRequiredAmountOfSongs(sortedSongs, numTopRatedSimilarSongs);

    }

    static List<Song> getRequiredAmountOfSongs(List<Song> songs, int numTopRatedSimilarSongs){
        return songs.stream().limit(numTopRatedSimilarSongs).collect(Collectors.toList());
    }

    static List<Song> getSortedSongs(List<Song> songs){
        Comparator<Song> byRating = (s1, s2) -> Float.compare(
                s2.getRating(), s1.getRating());
        return songs.stream().sorted(byRating).collect(Collectors.toList());
    }



    static List<Song> getSongsInNetwork(Song song, List<Song> songs) {
        if(!songs.contains(song)){
            songs.add(song);
            for(Song s: song.getSimilarSongs()){
                getSongsInNetwork(s, songs);
            }
        }
        return songs;
    }



}