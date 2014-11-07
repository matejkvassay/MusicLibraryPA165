/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.matejkvassay.musiclibrary.Service;

import java.util.List;
import sk.matejkvassay.musiclibrary.Dto.AlbumDto;
import sk.matejkvassay.musiclibrary.Dto.GenreDto;
import sk.matejkvassay.musiclibrary.Dto.MusicianDto;
import sk.matejkvassay.musiclibrary.Dto.SongDto;

/**
 *
 * @author Horak
 */
public interface SongService {
	/**
	 * Adds an Song into DB.
	 * @param song Song to be added.
	 */
    public void addSong(SongDto song);
	
	/**
	 * Removes Song from DB.
	 * @param song Song to be removed.
	 */
    public void removeSong(SongDto song);
	
	/**
	 * Updates Song.
	 * @param song Song to be updated.
	 */
    public void updateSong(SongDto song);
	
	/**
	 * Retreives all songs from DB.
	 * @return List of retreived songs.
	 */
    public List<SongDto> getAllSongs();
	
	/**
	 * Retreives songs with specific title.
	 * @param nameOfSong Part of the name of the song.
	 * @return List of retreived songs.
	 */
	public List<SongDto> getSongsByName(String nameOfSong);
	
	/**
	 * Retreives song with specific id.
	 * @param id Id of the song.
	 * @return Song with specific ID.
	 */
	public SongDto getSongById(Long id);
	
	/**
	 * Retreives songs from specific album.
	 * @param album Album to which the songs belong.
	 * @return List of retreived songs.
	 */
	public List<SongDto> getSongsByAlbum(AlbumDto album);
	
	/**
	 * Retreives songs from specific musician.
	 * @param musician Musician who created those songs.
	 * @return List of retreived songs.
	 */
	public List<SongDto> getSongsByMusician(MusicianDto musician);
	
	/**
	 * Retreives songs with specific genre.
	 * @param genre Genre of the songs.
	 * @return List of retreived songs.
	 */
	public List<SongDto> getSongsByGenre(GenreDto genre);
}