package no.bouvet.kpro.persistence;

import java.net.URI;
import java.util.List;
import java.util.Set;

import no.bouvet.kpro.model.Media;
import no.bouvet.kpro.model.Part;

public interface VaudevilleStorage {

	public List<Part> getChildren(String partId);

	public List<Part> getChildren(Media event);

	public Set<Media> getAllMedia();

	public Media getMediaByURI(URI uri);

	public Media getMediaByFileName(String filename);
}
