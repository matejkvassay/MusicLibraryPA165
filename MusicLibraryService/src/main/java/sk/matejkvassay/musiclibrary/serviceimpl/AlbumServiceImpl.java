package sk.matejkvassay.musiclibrary.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import sk.matejkvassay.musiclibrary.dao.AlbumDao;
import sk.matejkvassay.musiclibrarybackendapi.dto.AlbumDto;
import sk.matejkvassay.musiclibrarybackendapi.dto.MusicianDto;
import sk.matejkvassay.musiclibrarybackendapi.dto.SongDto;
import sk.matejkvassay.musiclibrary.entity.Album;
import sk.matejkvassay.musiclibrary.entity.Musician;
import sk.matejkvassay.musiclibrary.entity.Song;
import sk.matejkvassay.musiclibrarybackendapi.service.AlbumService;

/**
 *
 * @author Matej Bordáč
 */
@Service
public class AlbumServiceImpl implements AlbumService {

    @Inject
    private PlatformTransactionManager txManager;
    
    @Inject
    private AlbumDao albumDao;
    
    public AlbumServiceImpl() {
    }
    
    @Override
    public void addAlbum(AlbumDto album) {
        TransactionStatus status = null;
        
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            albumDao.addAlbum(fromDto(album));
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status);
            }
            throw ex;
        }
    }

    @Override
    public void removeAlbum(AlbumDto album) {
        TransactionStatus status = null;
        
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            albumDao.removeAlbum(albumDao.getAlbumById(album.getId()));
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status);
            }
            throw ex;
        }
    }

    @Override
    public void updateAlbum(AlbumDto album) {
        TransactionStatus status = null;
        
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            albumDao.updateAlbum(fromDto(album));
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status);
            }
            throw ex;
        }
    }

    @Override
    public AlbumDto getAlbumById(Long id) {
        TransactionStatus status = null;
        Album album = null;
        
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            album = albumDao.getAlbumById(id);
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status);
            }
            throw ex;
        }
        
        return toDto(album);
    }

    @Override
    public List<AlbumDto> getAlbumsByName(String name) {
        TransactionStatus status = null;
        List<Album> albums = null;
        
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            albums = albumDao.getAlbumsByName(name);
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status);
            }
            throw ex;
        }
        
        List<AlbumDto> albumsDto = new ArrayList<>();
        if (albums != null) {
            for (Album album : albums) {
                albumsDto.add(toDto(album));
            }
        }
        
        return albumsDto;
    }

    @Override
    public AlbumDto getAlbumBySong(SongDto song) {
        TransactionStatus status = null;
        Album album = null;
        
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            Song songEntity = new Song();
            songEntity.setId(song.getId());
            album = albumDao.getAlbumBySong(songEntity);
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status);
            }
            throw ex;
        }
        
        return toDto(album);
    }

    @Override
    public List<AlbumDto> getAlbumsByMusician(MusicianDto musician) {
        TransactionStatus status = null;
        List<Album> albums = null;
        
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            Musician musicianEntity = new Musician();
            musicianEntity.setId(musician.getId());
            albums = albumDao.getAlbumsByMusician(musicianEntity);
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status);
            }
            throw ex;
        }
        
        List<AlbumDto> albumsDto = new ArrayList<>();
        if (albums != null) {
            for (Album album : albums) {
                albumsDto.add(toDto(album));
            }
        }
        
        return albumsDto;
    }

    @Override
    public List<AlbumDto> getAlbumsByDate(Date date) {
        TransactionStatus status = null;
        List<Album> albums = null;
        
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            albums = albumDao.getAlbumsByDate(date);
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status);
            }
            throw ex;
        }
        
        List<AlbumDto> albumsDto = new ArrayList<>();
        if (albums != null) {
            for (Album album : albums) {
                albumsDto.add(toDto(album));
            }
        }
        
        return albumsDto;
    }

    @Override
    public List<AlbumDto> getAllAlbums() {
        TransactionStatus status = null;
        List<Album> albums = null;
        
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            albums = albumDao.getAllAlbums();
            txManager.commit(status);
        } catch (DataAccessException ex) {
            if (!status.isCompleted()) {
                txManager.rollback(status);
            }
            throw ex;
        }
        
        List<AlbumDto> albumsDto = new ArrayList<>();
        if (albums != null) {
            for (Album album : albums) {
                albumsDto.add(toDto(album));
            }
        }
        
        return albumsDto;
    }
    
    
    
    public PlatformTransactionManager getTxManager() {
        return txManager;
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public AlbumDao getAlbumDao() {
        return albumDao;
    }

    public void setAlbumDao(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }
    
    
    public static AlbumDto toDto(Album album) {
        if(album == null){
            return null;
        }
        AlbumDto albumDto = new AlbumDto();
        albumDto.setId(album.getId());
        albumDto.setTitle(album.getTitle());
        albumDto.setMusician(MusicianServiceImpl.toDto(album.getMusician()));
        albumDto.setAlbumArt(album.getAlbumArt());
        albumDto.setDateOfRelease(album.getDateOfRelease());
        albumDto.setCommentary(album.getCommentary());

        return albumDto;
    }

    public static Album fromDto(AlbumDto albumDto) {
        if(albumDto == null){
            return null;
        }
        Album album = new Album();
        album.setId(albumDto.getId());
        album.setTitle(albumDto.getTitle());
        album.setMusician(MusicianServiceImpl.fromDto(albumDto.getMusician()));
        album.setAlbumArt(albumDto.getAlbumArt());
        album.setDateOfRelease(albumDto.getDateOfRelease());
        album.setCommentary(albumDto.getCommentary());

        return album;
    }
    
}
