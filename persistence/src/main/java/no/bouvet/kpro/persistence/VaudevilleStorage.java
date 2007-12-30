package no.bouvet.kpro.persistence;

import java.net.URI;
import java.util.List;
import java.util.Set;

import no.bouvet.kpro.model.old.Media;
import no.bouvet.kpro.model.old.Part;

public interface VaudevilleStorage {

	public List<Part> getChildren(String partId);

	public List<Part> getChildren(Media event);

	public Set<Media> getAllMedia();

	public Media getMediaByURI(URI uri);

	public Media getMediaByFileName(String filename);
}
