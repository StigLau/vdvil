//import org.testng.annotations.*
import no.bouvet.kpro.renderer.Renderer
import no.bouvet.kpro.renderer.Instructions
import no.bouvet.kpro.renderer.audio.*
import no.bouvet.kpro.renderer.audio.SimpleAudioInstruction

class AudioRendererWithGroovyTest {


    List<AudioSource> audioSources = new ArrayList();
    Renderer renderer

    //@BeforeMethod
    def setUp() {
    }

    //@AfterMethod
    def after() {
        for (audioSource in audioSources) {
            audioSource.close()
        }
    }



    //@Test
    void testMix2() {
        String snapMp3 = getClass().getResource("/Snap_-_Rhythm_is_a_Dancer.mp3").getFile();
        String coronaMp3 = getClass().getResource("/Corona_-_Baby_Baby.mp3").getFile();
        String testMp3 = getClass().getResource("/test.mp3").getFile();

        AudioSource sourceA = AudioSourceFactory.load(new File(coronaMp3));
        audioSources.add(sourceA)
        AudioSource sourceB = AudioSourceFactory.load(new File(testMp3))
        audioSources.add(sourceB)

        Instructions instructions = createSimpleInstructions(sourceA, sourceB);
        playMusic(instructions)
    }


    private static Instructions createSimpleInstructions(AudioSource sourceA, AudioSource sourceB) {
        Instructions instructions = new Instructions();
        Float snapOffset = 44100 * 1.99f
        //instructions.append(new SimpleAudioInstruction(0, 256, 136.7f, 128, snapOffset, sourceA));




        Float coronaOffset = 44100 * 0.445f
        //instructions.append(new SimpleAudioInstruction(0, 256, 136.7f, 0, coronaOffset, sourceA));
        instructions.append(new SimpleAudioInstruction(0, 256, 132.98f, 0, coronaOffset, sourceA));

        //Baby Baby Numbers
        def numbers = [0, //Baby, why can't we just stay together
                16, //Baby baby, Why can't we just say forever
                32, //Intro
                64, //Riff 1. time
                96, //1. Refrain  I want to roll inside your soul,
                128, //2. Verse - Caught you down by suprise
                128 + 32, //Baby baby, why can't we just stay together
                128 + 64, //riff 2. time
                128 + 96, // Deep inside I know you need it
                256,         //Caught you down by suprise
                256 + 32, // Baby baby
                256 + 64,  // Baby baby
                256 + 96,  //Medley - Wheey
                384,
                384 + 32, // Riff 3. time
                384 + 64, //Baby Baby
                384 + 96  //Baby Baby, Why can't we just say forever  - End
        ]
        for (number in numbers) {
            instructions.append(new SimpleAudioInstruction(number , number +1, 132.98f, 0, 0, sourceB));
        }



        //instructions.append(new SimpleAudioInstruction(0, 16, 120, 0, 555679, sourceA));
        //instructions.append(new SimpleAudioInstruction(16, 31, 120, 16, 555679, sourceA));
        //instructions.append(new SimpleAudioInstruction(16, 31, 120, 3843712, sourceB));
        return instructions;
    }
}