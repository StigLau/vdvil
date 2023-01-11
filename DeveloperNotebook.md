Notes when programming Vdvil

# Romjula 2009/2010

 * Immense cleanup job of old KPRO2007 project
 * Removed lots of stale code
 * Simplifyed usage of Vaudeville renderers and playback to a sleek sexy API
 * Moved project from internal to GitHub repository

### Todo's
 * TaggerGUIScala should use the new improved downloader to fetch files
 * Downloader should cater for .dvl's and mp3's, not songs. However, multiple songs should be added by outside GUI.. Not quite sure how to implement this...
 * Probable timing problem between different renderers. Need to implement functionality to synchronize renderers inter-vm or via HTTP.
 * Implement XML storage for image .dvl's
 * Implement new self-made multimedia/file-cache
 * Problem of architecture when closing renderer views because of images have stopped having have to be shown

## Undoing releases
Deleting remote tags can be done with:  git push origin :root-0.7


## Dirigent Architectural Description
Dirigenten, which has not been implemented yet, is responsible for downloading, parsing and playing all the multimedia content.
It is a higher-level concept which just holds together the major building blocks of Vdvil

Functions:
 * play/stop
 * startAtCue (120 beats)
 * playbackSpeed (130.5 BPM)
 * loadComposition(url)

### Downloaders
Responsible for downloading files from different file transfer protocols
Examples of systems:
 * httpCache
 * spotify
 * torrent
VdvilCache has the details
 * Note: Vdvil should preferrably have control of its own cache storage since not all downloaders have their own caching solution
 * Note: MimeType/HTTP Headers should be stored in cache!

 ### Renderers
  * MP3
  * JPG/image
  * Vdvil
  * Composition
Should also know how to read the different metadata/xml file formats.
Based on MimeType
Functions:
 * load(File location)
 * load(InputStream inputStream)
 * accepts(String mimeType)


 ## Finished todos

  * Some tests depend on .dvl and .mp3 files that should be part of the project so anyone can build without errors in tests
   * Introduce Psylteflesk .dvl/.mp3
  * Create API for describing how resources (.mp3s, videos aso) can be downloaded and locally cached, and referenced by the renderers.
  * FactorMagic should perhaps be removed!

  * !!!! TaggerGUI Does not stop playback

  * Rewrite CompositionExample so it can be persisted to file
  * Move Domain into case class
  * Persistence to XML
  * Load compositions from web instead of from hardcoded
  * TaggerGUIScala should show URL of files and segment identifiers
  * Now downloads the world - needs to cut down on downloading
