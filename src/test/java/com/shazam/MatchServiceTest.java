package com.shazam;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnitParamsRunner.class)
public class MatchServiceTest {

    private static MatchService matchService;
    private static Song songA;
    private static Song songB;
    private static Song songC;
    private static Song songD;

    @BeforeClass
    public static void beforeClass(){
        matchService = new MatchService();
        songA = new Song("A", 1.1f);
        songB = new Song("B", 3.3f);
        songC = new Song("C", 2.5f);
        songD = new Song("D", 4.7f);

        songA.addSimilarSong(songB);
        songA.addSimilarSong(songC);
        songB.addSimilarSong(songD);
        songC.addSimilarSong(songD);
    }


    //ShouldReturnCorrectNumberOfRequestSongs
    @Test
    @Parameters({
            "0, 0",
            "1, 1",
            "2, 2",
            "4, 2",
    })
    public void getRequiredAmountOfSongsTest(int numTopRatedSimilarSong, int expectedResult) {
        List<Song> listOfSongs = songA.getSimilarSongs();
        List<Song> requiredAmountOfSongs = matchService.getRequiredAmountOfSongs(listOfSongs, numTopRatedSimilarSong);
        int actualResult = requiredAmountOfSongs.size();
        assertEquals(expectedResult,actualResult);
    }




    //ShouldReturnTheSongsInCorrectOrder
    @Test
    public void getSortedSongsTest(){
        List<Song> songs = asList(songB, songD, songA, songC);
        List<Song> actualResult = matchService.getSortedSongs(songs);
        List<Song> expectedResult = asList(songD, songB, songC, songA);
        assertEquals(actualResult, expectedResult);
    }



    //ShouldReturnTheSongsInNetwork
    @Test
    public void getSongsInNetworkTest(){
        List<Song> songs = new ArrayList<>();
        List<Song> actualResult = matchService.getSongsInNetwork(songA, songs);
        List<Song> expectedResult = asList(songA, songB, songD, songC);
        assertEquals(actualResult, expectedResult);
    }


}