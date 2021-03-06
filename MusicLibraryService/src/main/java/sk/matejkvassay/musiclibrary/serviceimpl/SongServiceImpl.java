package sk.matejkvassay.musiclibrary.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import sk.matejkvassay.musiclibrary.dao.SongDao;
import sk.matejkvassay.musiclibrarybackendapi.dto.AlbumDto;
import sk.matejkvassay.musiclibrarybackendapi.dto.GenreDto;
import sk.matejkvassay.musiclibrarybackendapi.dto.MusicianDto;
import sk.matejkvassay.musiclibrarybackendapi.dto.SongDto;
import sk.matejkvassay.musiclibrary.entity.Album;
import sk.matejkvassay.musiclibrary.entity.Genre;
import sk.matejkvassay.musiclibrary.entity.Musician;
import sk.matejkvassay.musiclibrary.entity.Song;
import sk.matejkvassay.musiclibrarybackendapi.service.SongService;

/**
 *
 * @author Horak
 */
@Service
public class SongServiceImpl implements SongService {

    @Inject
    private PlatformTransactionManager txManager;

    @Inject
    private SongDao songDao;

    public PlatformTransactionManager getTxManager() {
        return txManager;
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public SongDao getSongDao() {
        return songDao;
    }

    public void setSongDao(SongDao songDao) {
        this.songDao = songDao;
    }

    public static SongDto toDto(Song song) {
        if (song == null) {
            return null;
        }

        SongDto songDto = new SongDto();
        songDto.setId(song.getId());
        songDto.setBitrate(song.getBitrate());
        songDto.setCommentary(song.getCommentary());
        songDto.setPositionInAlbum(song.getPositionInAlbum());
        songDto.setTitle(song.getTitle());

        if (song.getGenre() != null) {
            songDto.setGenre(GenreServiceImpl.entityToDto(song.getGenre()));
        }
        if (song.getMusician() != null) {
            songDto.setMusician(MusicianServiceImpl.toDto(song.getMusician()));
        }
        if (song.getAlbum() != null) {
            songDto.setAlbum(AlbumServiceImpl.toDto(song.getAlbum()));
        }

        return songDto;
    }

    public static Song fromDto(SongDto songDto) {
        if (songDto == null) {
            return null;
        }

        Song song = new Song();
        song.setId(songDto.getId());
        song.setBitrate(songDto.getBitrate());
        song.setCommentary(songDto.getCommentary());
        song.setPositionInAlbum(songDto.getPositionInAlbum());
        song.setTitle(songDto.getTitle());

        if (songDto.getGenre() != null) {
            song.setGenre(GenreServiceImpl.dtoToEntity(songDto.getGenre()));
        }
        if (songDto.getMusician() != null) {
            song.setMusician(MusicianServiceImpl.fromDto(songDto.getMusician()));
        }
        if (songDto.getAlbum() != null) {
            song.setAlbum(AlbumServiceImpl.fromDto(songDto.getAlbum()));
        }

        return song;
    }

    @Override
    public void addSong(SongDto songDto) {
        Song song = fromDto(songDto);

        TransactionStatus status = null;
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            songDao.addSong(song);
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status); //if commit failed, it is cleaned, no rollback call needed as said in documantation
            }
            throw ex;
        }
    }

    @Override
    public void removeSong(SongDto songDto) {
        TransactionStatus status = null;

        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            Song songToRemove = songDao.getSongById(songDto.getId());
            songDao.removeSong(songToRemove);
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status); //if commit failed, it is cleaned, no rollback call needed as said in documantation
            }
            throw ex;
        }
    }

    @Override
    public void updateSong(SongDto song) {
        Song songToUpdate = fromDto(song);

        TransactionStatus status = null;
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            songDao.updateSong(songToUpdate);
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status); //if commit failed, it is cleaned, no rollback call needed as said in documantation
            }
            throw ex;
        }
    }

    private List<SongDto> songsListToDto(List<Song> songs) {
        List<SongDto> songsDto = new ArrayList<>();

        if (songs != null) {
            for (Song s : songs) {
                songsDto.add(toDto(s));
            }
        }

        return songsDto;
    }

    @Override
    public List<SongDto> getAllSongs() {
        TransactionStatus status = null;
        List<Song> songs = null;

        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            songs = songDao.getAllSongs();
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status); //if commit failed, it is cleaned, no rollback call needed as said in documantation
            }
            throw ex;
        }

        List<SongDto> songsDto = songsListToDto(songs);

        return songsDto;
    }

    @Override
    public List<SongDto> getSongsByName(String nameOfSong) {
        TransactionStatus status = null;
        List<Song> songs = null;

        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            songs = songDao.getSongsByName(nameOfSong);
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status); //if commit failed, it is cleaned, no rollback call needed as said in documantation
            }
            throw ex;
        }

        List<SongDto> songsDto = songsListToDto(songs);

        return songsDto;
    }

    @Override
    public SongDto getSongById(Long id) {
        TransactionStatus status = null;
        Song song;

        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            song = songDao.getSongById(id);
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status); //if commit failed, it is cleaned, no rollback call needed as said in documantation
            }
            throw ex;
        }

        SongDto songDto = toDto(song);

        return songDto;
    }

    @Override
    public List<SongDto> getSongsByAlbum(AlbumDto album) {
        TransactionStatus status = null;
        List<Song> songs;

        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            Album albumEntity = new Album();
            albumEntity.setId(album.getId());       //only this is needed for DAO layer
            songs = songDao.getSongsByAlbum(albumEntity);
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status); //if commit failed, it is cleaned, no rollback call needed as said in documantation
            }
            throw ex;
        }

        List<SongDto> songsDto = songsListToDto(songs);

        return songsDto;
    }

    @Override
    public List<SongDto> getSongsByMusician(MusicianDto musician) {
        TransactionStatus status = null;
        List<Song> songs;

        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            Musician musicianEntity = new Musician();
            musicianEntity.setId(musician.getId());
            songs = songDao.getSongsByMusician(musicianEntity);
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status); //if commit failed, it is cleaned, no rollback call needed as said in documantation
            }
            throw ex;
        }

        List<SongDto> songsDto = songsListToDto(songs);

        return songsDto;
    }

    @Override
    public List<SongDto> getSongsByGenre(GenreDto genre) {
        TransactionStatus status = null;
        List<Song> songs;

        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            Genre genreEntity = new Genre();
            genreEntity.setId(genre.getId());
            songs = songDao.getSongsByGenre(genreEntity);
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status); //if commit failed, it is cleaned, no rollback call needed as said in documantation
            }
            throw ex;
        }

        List<SongDto> songsDto = songsListToDto(songs);

        return songsDto;
    }
}
