package sk.matejkvassay.musiclibrary.validation;

import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sk.matejkvassay.musiclibrarybackendapi.dto.SongDto;
import sk.matejkvassay.musiclibrarybackendapi.service.SongService;

/**
 *
 * @author
 */
@Component
public class SongSpringValidation implements Validator {

    @Inject
    private SongService songService;

    @Override
    public boolean supports(Class<?> type) {
        return SongDto.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SongDto song = (SongDto) o;
        if (song.getTitle() == "" || song.getTitle() == null || song.getTitle().trim().length() == 0) {
            errors.rejectValue("title", "song.nameempty");
        }
        
        if (song.getBitrate() < 1){
            errors.rejectValue("bitrate", "song.invalidbitrate");
        }
        
        if (song.getPositionInAlbum() < 1){
            errors.rejectValue("positionInAlbum", "song.invalidposition");
        }

        if (song.getMusician() == null) {
            errors.rejectValue("musician", "empty.musician");
        }

        if (song.getGenre() == null) {
            errors.rejectValue("genre", "empty.genre");
        }

        if (song.getAlbum() == null) {
            errors.rejectValue("album", "empty.album");
        }

        if (song.getAlbum() != null) {
            List<SongDto> songs = songService.getSongsByAlbum(song.getAlbum());

            for (SongDto songDto : songs) {
                //if position is already occupied in DB by another song
                if(songDto.getPositionInAlbum() == song.getPositionInAlbum() && !songDto.equals(song)){
                    errors.rejectValue("positionInAlbum", "song.positionInAlbum.notunique");
                }
            }
        }
    }

}
