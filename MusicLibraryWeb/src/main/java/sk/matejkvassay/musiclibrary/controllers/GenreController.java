package sk.matejkvassay.musiclibrary.controllers;

import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import sk.matejkvassay.musiclibrary.validation.GenreSpringValidation;
import sk.matejkvassay.musiclibrarybackendapi.dto.GenreDto;
import sk.matejkvassay.musiclibrarybackendapi.service.GenreService;
import sk.matejkvassay.musiclibrarybackendapi.service.SongService;

/**
 *
 * @author Matej Kvassay <www.matejkvassay.sk>
 */
@Controller
@RequestMapping("/genre")
public class GenreController {
    
    @Inject
    private GenreService genreService;
    @Inject
    private SongService songService;
    @Inject
    private MessageSource messageSource;
    @Inject 
    private GenreSpringValidation validator;

    public GenreService getGenreService() {
        return genreService;
    }

    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    
    
    @ModelAttribute("allGenres")
    public List<GenreDto> allGenres() {
        return genreService.getAllGenres();
    }
    
    
   
    @RequestMapping(value="/new", method=RequestMethod.GET)
    public String edit(Model model){
        model.addAttribute("genre",new GenreDto());
        return "genre/edit";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("genres", new GenreDto());
        return "genre/list";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model,RedirectAttributes redirectAttributes, Locale locale, UriComponentsBuilder uriBuilder){
        GenreDto genre=genreService.findGenreById(id);
        model.addAttribute("genre", genre);
        model.addAttribute("songs", songService.getSongsByGenre(genre));
        return "genre/detail";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes, Locale locale, UriComponentsBuilder uriBuilder) {
        GenreDto genre = genreService.findGenreById(id);
        genreService.removeGenre(genre);
        redirectAttributes.addFlashAttribute(
                "message",
                messageSource.getMessage("genre.delete.message", new Object[]{genre.getName()}, locale)
        );
        return "redirect:" + uriBuilder.path("/genre/list").build();
    }
    
    @InitBinder("genre")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }
    
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable long id, Model model) {
        GenreDto genre = genreService.findGenreById(id);
        model.addAttribute("genre", genre);
        return "genre/edit";
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("genre") GenreDto genre, BindingResult bindingResult, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, Locale locale) {

        if (bindingResult.hasErrors()) {
            return "genre/edit";
        }
        
        //create new
        if (genre.getId() == null) {
            genreService.addGenre(genre);
            redirectAttributes.addFlashAttribute(
                    "message",
                    messageSource.getMessage("genre.add.message", new Object[]{genre.getName()}, locale)
            );
            
        //update existing
        } else {
            genreService.updateGenre(genre);
            redirectAttributes.addFlashAttribute(
                    "message",
                    messageSource.getMessage("genre.updated.message", new Object[]{genre.getName()}, locale)
            );
        }
        return "redirect:" + uriBuilder.path("/genre/list").build();
    }
}
