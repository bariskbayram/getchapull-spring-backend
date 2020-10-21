package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.model.Album;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeAlbumDao")
public class FakeAlbumDao implements DataAccessAlbum {

    private static List<Album> albums = new ArrayList<>();

    static {
        albums.add(new Album(UUID.randomUUID(), "", "", "", ""));
    }

    @Override
    public List<Album> getAllAlbums() {
        return albums;
    }

    @Override
    public void addAlbum(Album album) {
        albums.add(album);
    }

    @Override
    public Optional<Album> getAlbumById(UUID id) {
        return albums.stream().filter( album -> album.getId().equals(id)).findFirst();
    }

    @Override
    public int deleteAlbumById(UUID id) {
        Optional<Album> album = getAlbumById(id);
        if(album == null){
            return 0;
        }
        albums.remove(album.get());
        return 1;
    }

    @Override
    public int updateAlbumById(UUID id, Album update) {
        return getAlbumById(id)
                .map(p -> {
                    int index = albums.indexOf(p);
                    if(index >=0){
                        albums.set(index, new Album(id, update.getBand(), update.getName(), update.getYear(), update.getCoverLink().get()));
                        return 1;
                    }
                    return 0;
                }).orElse(0);
    }
}
