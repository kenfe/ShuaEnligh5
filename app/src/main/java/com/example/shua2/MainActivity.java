package com.example.shua2;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.*;
import java.lang.Thread.*;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private String lastMissedTTS;

    private Button btnStart;
    private Button btnRepeat;
    private Button btnString;
    private TextView textViewProgress;
    private TextView textViewString;

    private SeekBar seekbarProgress;

    ToggleButton[] tButtons = new ToggleButton[30];

    ToggleButton[] tButtonss = new ToggleButton[3];

    private final Bundle params = new Bundle();

    private int count = -1;
    private int maxCount = 10;
    private int randomValue;
    private int[] sequence;

    private static Random random;

    int wordCount = 0;
    int lessonNum = 30;

    private int[] lessonNumber;


    // 단어와 위치를 저장할 리스트
    private List<Pair<Integer, String>> shownWordPairs = new ArrayList<>();


    //String[][] lessons = new String[31][20];
/*    String[] lessons = {"am,are,is","wake up","today","weather","windy","cloudy","sunny","snowy","raining","writer","apple","it","hi","hello","goodbye","name","book","egg","bye","meet"
                        ,"one","two","three","four","five","six","seven","eight","nine","ten","grade","piano","school","happy","bike","bake","butter","sugar","flour","mix"
                        ,"clap","jump","place","notebook","kick","animal","monkey","I can","ant", "teacher", "pencil case","igloo","zoo","eraser","elephant","bear","crayon","thing","people","ruler"
                        ,"this","that","dance","climb","first","second","swim","scissors","glue","marker","sketchbook","look","circle","count","correct","spend","socks","scarf","buy","mittens"
                        ,"hold on","let me help","ready","doll","top","toy blocks","skateboard","water gun","robot","bench","brush","fox","table","glass","peach","dollar","ring","student","pen","write"
                        ,"my","your","his","her","their","our","play soccer","ride a bike","fly a kite","sing a song","puzzle","dollhouse","inline skates","birthday","laundry","T=shirts","pants","festival","take out","clothes"
                        ,"carry","bee","yo-yo","bag","hat","car","how","drive","what","when","why","who","fish","where","which","banana","match","do","like","down"
                        ,"close","come","open","sit","stand","sorry","rhyming","case","vase","cape","tape","gate","cave","wave","stop","bird","kangaroo","sure","penguin","borrow"
                        ,"soccer","tennis","baseball","basketball","volleyball","dodge ball","consonant","vowel","capital","draw","choose","alphabet","word","sentence","letter","elementary","mistake","tiger","panda","folder"
                        ,"father","mouse","bread","toaster","washing machine","friend","kiwi","brother","wait","orange","many","line","save","vacuum","refrigerator","small","air conditioner","hungry","empty","zero"
                        ,"don't have","have","chalk","table tennis", "hockey","billiards","golf","can't","sports","cool","not","young","full","old","sad","town","earth","doctor","cook","singer"
                        ,"eyes","nose","mouth","ears","hands","feet","nurse","farmer","pilot","ball park","police officer","alien","get on","library","toy store","get off","flag","chicken","tunnel","water bottle"
                        ,"cookie","eleven","twelve","arms","legs","tail","horn","body","part","check","actor","short","tomato","form","say","potato","chopsticks","fork","plate","bowl"
                        ,"You're welcome","spoon","funny","grumpy","kind","smart","brave","shy","mole","white","turn","theater","crowded","twenty","aquarium","cheese","amusement park","foreigner","whose","classroom"
                        ,"napkin","pick up","straw","jar","cheerful","messy","talkative","curious","personality","mine","hers","theirs","yours","his","ours","house","perfect","taegwondo","balloon","a lot of"
                        ,"tired","follow","firefighter","chef","bus driver","aunt","uncle","shirt","these","shoes","dress","gloves","those","dragon","fight","throw","roll","bounce","hit","run away"
                        ,"cat","game","bat","umbrella","jump rope","scientist","vet","artist","astronaut","angry","office worker","homemaker","hairstylist","dad","grandpa","mom","air plane","possessive","adjective","dialogue"
                        ,"sleepy","birthday","juice","Don't ~","dangerous","pretty","handsome","carrot","onion","bean","cucumber","spinach","cloth","vegetable","grammar","zebra","rabbit","horse","smaller","bigger"*/

/*            String[] lessons = {
                        "sister","cute","pretty","tall","weak","move","strong","garlic","feed","mushroom","cabbage","everything","pig","like","don't like","negative","lion","snake","frog","turtle"
                        ,"hole","pole","hope","rope","nose","cube","tube","dune","tune","cute","mute","spider","roof","giraffe","do","whale","ruin","pet","pool","swallow"
                        ,"blue","green","purple","red","yellow","butterfly","flower","sky","star","color","read books","play games","listen to music","picture","watch TV","simple","present","beautiful","card","question"
                        ,"straight","violin","virus","Excuse me","How much","expensive","China","play with clay","take pictures","together","always","crab","polar bear","shark","octopus","North Pole","grape","dolphin","fur","bamboo"
                        ,"snowing","outside","want","wear","blouse","sneakers","shrimp","vest","milk","squid","jeans","fruit","jellyfish","boots","wonderful","stingray","animal","practice","ocean","face"
                        ,"website","yacht","Canada","Italy","France","Greece","planet","skirt","beverages","newspaper","now","play","ride","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"
                        ,"toy store","bakery","market","police station","tie","shorts","sweater","jacket","speak","today","swimsuit","Chinese","recycle","guitar","English","reuse","about","yourself","can read","boy"
                        ,"January","February","March","April","May","June","July","August","September","October","November","December","week","weekend","day","night","evening","midnight","noon","afternoon"
                        ,"bank","hospital","mall","airport","museum","tongue","finger","stick","fly","walk","run","hike","tree","chair","sick","fever","cold","toothache","stomachache","headache"
                        ,"vacation","holiday","season","for","spring","summer","autumn","winter","fall","bruise","eat","cause","pain","hurt","disease","backache","earache","rash","pimple","flu"
                        ,"chart","pizza","snap","cup","man","nail","statue","beet","bow","them","twins","best friends","preposition","on","in","under","behind","next to","in front of","box"
                        ,"salt","pepper","hay","honey","mango","watermelon","bed","table","strawberry","candy","TV","dresser","water","key","computer","sandcastle","machine","magazine","flower","lifeguard"
                        };*/

    // 1-5
    //"Clean","Read","Write","Draw","Talk","Drink","Sleep","Study","Skin","Brain","Bone","Heal","Pajamas","Porch","Follow","Focus","Idea","Travel around the world","Field","Wear",  "Grow up","Cure","diseases","Umbrella","Pilot","Space shuttle","Spacesuit", "White coat","Hospital","Uniform","Become","Both","Sunglasses","Ocean","Hold","Seaweed","Strong","Float","Take care of sick people","Wrap","half",  "plan","stay home", "go on a trip","decide","relax","expensive","everyone","library","active","stay inside","go outside","volleyball","far","cheap","maybe","mall","close","travel","arcade","habit", "watch a movie","listen to music","talk on the phone","take a rest","take a picture","cricket","make a mask","bake cookies","dragonfly","lie down","run away","degree","wake up","temperature","Celsius","Tradition","Lucky","Thermometer","Enjoy","Pen pal", "Introduce","Delicious","Activity","Festival","Country","Lovely","Join","Foreigner","Order","Garbage","Vacation","Sometimes","Advice","Interview","Project","Enough","Favorite","A little","Practice","friendly",

    //26-30
    //"at","on","in","start","have","go swimming","lesson","fluffy","go skating","bakery","create","anyway","yet","add","producer","outgoing","care about","along","weekly","change into","roll","baker","boil","flat","spread","crunchy","chewy","secede","try","popular","shovel","this","giraffe","that","cell phone","caterpillar","circle","coupon","giant","easily","problem","circus","posterior","beach","aquarium","pool","theater","stadium","palace","amusement park","child","children","law","extra","parent","submit","dozen","weigh","wonder","afraid","price","erase","broccoli","fresh","jar","artist","mistake","mask","soak up","stinky","drive","letter","baseball","picture","skate","dinner","walk the dog","cook dinner","practice the piano","wash the dishes","pick apples",
//            String[] lessons = {
//                    "at","on","in","start","have","go swimming","lesson","fluffy","go skating","bakery","create","anyway","yet","add","producer","outgoing","care about","along","weekly","change into","roll","baker","boil","flat","spread","crunchy","chewy","secede","try","popular","shovel","this","giraffe","that","cell phone","caterpillar","circle","coupon","giant","easily","problem","circus","posterior","beach","aquarium","pool","theater","stadium","palace","amusement park","child","children","law","extra","parent","submit","dozen","weigh","wonder","afraid","price","erase","broccoli","fresh","jar","artist","mistake","mask","soak up","stinky","drive","letter","baseball","picture","skate","dinner","walk the dog","cook dinner","practice the piano","wash the dishes","pick apples",
//            };


    String[][] lessonss = {

            {"engineer", "judge", "fur", "survive", "south", "faraway", "hide", "chef", "movie director", "explorer", "reporter", "travel", "goose", "stay", "firefighter", "writer", "architect", "fashion designer", "trip", "turn"},
            {"invent", "enemy", "opposite", "language", "remain", "arrest", "sesame", "describe", "rumor", "costume", "especially", "territory", "imitate", "adopt", "directly", "electricity", "decrease", "fastener", "foreign", "rescue"},
            {"garage", "suffer", "growth", "admission", "astronaut", "effect", "shortcut", "deny", "available", "difference", "blender", "ashamed", "passenger", "instruct", "industry", "journalist", "collection", "blame", "caterpillar", "instance"},
            {"octopus", "melt", "toothpick", "board", "microscope", "trumpet", "cymbals", "umbrella", "some", "separate", "important", "snowflake", "triangle", "harp", "hard", "strawberry", "soliloquy", "discovery", "recorder", "xylophone"},
            {"quarter", "previous", "complain", "parking lot", "orphanage", "educate", "shine", "remind", "simply", "lonely", "client", "shake", "press", "improve", "academic", "knowledge", "lay", "blood", "decade", "risk"},

            {"similar", "stick", "disable, disabled", "niece", "focus", "silent", "cooker", "ski pole", "equipment", "shoot", "faithful", "villager", "bitter", "latest", "net", "sled", "weed", "remove", "display", "puck"},
            {"gargle", "barber", "accident", "greedy", "celebrate", "planner", "million", "exist", "behave", "experience", "iceberg", "arise", "population", "awake", "regard", "replace", "obsession", "weigh", "discussion", "alive"},
            {"brush", "scientist", "arrow", "hunter", "acronym", "eat out", "go on a picnic", "magician", "nighttime", "remember", "shield", "goddess", "take a nap", "surf the internet", "am/are/is", "spot", "great", "myth", "read comics", "hang out with friends"},
            {"suddenly", "courage", "common", "optician", "illegal", "purpose", "sneeze", "debt", "spoil", "escape", "abroad", "communicate", "adventure", "saving(s)", "university", "independent", "lawyer", "arrange", "apologize", "symphony"},
            {"desert", "unique", "bottom", "well", "computer", "do the dishes", "tennis", "avoid", "antonym", "grass", "breakfast", "go fishing", "amount", "benevolence", "area", "straw", "upside down", "have", "go skating", "get up"},

            {"entrance", "partner", "protect", "succeed", "obey", "digestion", "artificial", "negative", "college", "persuade", "rely", "bronze", "conclude", "thought", "invade", "carbonate, carbonated", "average", "bother", "editor", "publish"},
            {"loudly", "secrete", "apartment", "weather", "hundred", "wrap", "waterfall", "quietly", "social", "danger", "build", "agent", "sheepdog", "serious", "happily", "huge", "safe", "nearby", "middle", "alive"},
            {"faster", "slowest", "most colorful", "most graceful", "smaller", "more energetic", "more dangerous", "fastest", "colorful", "graceful", "larger", "smallest", "most energetic", "most dangerous", "slower", "more colorful", "more graceful", "largest", "energetic", "dangerous"},
            {"catch", "take a photo", "housewarming", "match", "within", "promise", "duty", "raise", "broom", "meaning", "luck", "breathe", "stage", "heaven", "touch", "witch", "present", "puppet", "provide", "scare away"},
            {"either", "foundation", "charity", "machine", "aware", "criticize", "individual", "forward", "elsewhere", "familiar", "complete", "relationship", "cheer", "custom", "false", "ideal", "advertise", "laboratory", "against", "muscle"},

            {"create", "damage", "clothes", "hook", "admire", "steam", "plenty", "painting", "furniture", "version", "kingdom", "bloom", "soil", "imagination", "flood", "hang", "repaint", "warn", "harbor", "scare"},
            {"deaf", "customer", "harmony", "distance", "due", "pleasure", "leftover", "expand", "attempt", "scientific", "international", "confuse, confused", "disappear", "ancestor", "devote", "guilty", "argument", "acquire", "retire", "disable, disabled"},
            {"tooth, teeth", "guess", "footprint", "borrow, borrowed books", "mail, mailed a package", "daytime", "scene", "dinosaur", "flat", "take, took a walk", "go, went shopping", "eat, ate dinner", "repair", "gardener", "fossil", "ancient", "height", "get, got a haircut", "absent", "greet"},
            {"compose", "reject", "span", "attractive", "device", "benefit", "liquid", "calculator", "awful", "seasonal", "otherwise", "desire", "empire", "disaster", "annual", "loose", "dynasty", "ignore", "extra", "unfortunately"},
            {"at", "restaurant", "epilogue", "collect", "whole", "side", "poem", "posthumous", "playground", "bakery", "skull", "museum", "along", "symbol", "gym", "hospital", "cliff", "skeleton", "famous", "Europe"},

            {"bite", "good-looking", "blind", "folktale", "backward", "hometown", "mostly", "fold", "rent", "immovable", "movement", "terrible", "while", "tight", "professor", "precise", "lamb", "flow", "exhibit", "whenever"},
            {"smile, smiled", "run, ran fast", "dance, danced beautifully", "try, tried", "giant", "jaw", "powerful", "walk, walked slowly", "sing, sang loudly", "bake, baked", "study, studied", "tear", "rake", "ton", "write, wrote neatly", "jump, jumped high", "talk, talked quietly", "cry, cried", "reach", "gather"},
            {"solve", "cancel", "echo", "autumn", "response", "responsible", "debate", "wealth", "strict", "length", "netizen", "design", "deliver", "bomb", "chemical", "aim", "announce", "attitude", "separately", "traditionally"},
            {"do, did", "drink, drank", "eat, ate", "incise", "ground", "favorite", "plate", "have, had", "go, went", "come, came", "hike", "guide", "malcontent", "fit", "read, read", "see, saw", "sandcastle", "real", "hone", "look for"},
            {"shock", "therefore", "central", "satisfy", "spill", "marriage", "charge", "hunger", "require", "fiery", "creature", "nag", "boast", "convenient", "organize", "religion", "drown", "measure", "achieve", "transportation"},

            {"sand dune", "cactus", "bush", "stem", "lizard", "see a waterfall", "surprisingly", "climb a mountain", "go on safari", "explore the jungle", "explore a cave", "walk in the forest", "Thursday", "Saturday", "heavily", "go to a desert", "visit an island", "Wednesday", "Tuesday", "shady"},
            {"regret", "stir", "fortunate", "reply", "advance", "purchase", "clinic", "obtuse", "tease", "athlete", "logical", "seek", "appearance", "memorize", "forgive", "polio", "adapt", "challenge", "expression", "medical"},
            {"sandstorm", "take a rest", "outside", "bury", "sandy", "freezing", "rocky", "snowstorm", "obsolete", "good grades", "get along", "regularly", "exercise", "school president", "volunteer work", "equal", "past", "healthy food", "country", "quite"},
            {"couch", "pill", "global", "baggage", "quality", "admit", "grain", "rude", "error", "positive", "championship", "harm", "parallelogram", "journey", "businessman", "male", "leather", "recently", "example", "upper"},
            {"freedom", "grateful", "fault", "advise", "unfold", "express", "teenager", "appear", "confident", "produce", "loss", "divide", "soldier", "found", "decide", "period", "attend", "pattern", "capital", "brain"}
    };

    String[] lessons;

    boolean[] lessonSelect = new boolean[lessonNum];

    private ToggleButton autoModeToggle;
    private boolean isAutoMode = false; // 자동 모드 활성화 여부
    private Handler autoHandler = new Handler(); // 자동 모드 실행 핸들러
    private Runnable autoModeRunnable; // 자동 모드 작업


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.startBtn);
        btnStart.setText("Ready");

        btnRepeat = (Button)findViewById(R.id.btnRepeat);
        btnString = (Button)findViewById(R.id.btnShow);

        btnRepeat.setEnabled(false);
        btnString.setEnabled(false);

        textViewProgress = (TextView)findViewById(R.id.textProgress);
        textViewString = (TextView)findViewById(R.id.textViewString);

        seekbarProgress = (SeekBar)findViewById(R.id.seekBarProgress);
        //seekbarProgress.setMax(lessons.length);
        seekbarProgress.setProgress(0);

        for (int i=0; i < lessonNum;i++)
            lessonSelect[i] = true;

        tButtons[0] = (ToggleButton) findViewById(R.id.toggleButton);
        tButtons[1] = (ToggleButton) findViewById(R.id.toggleButton2);
        tButtons[2] = (ToggleButton) findViewById(R.id.toggleButton3);
        tButtons[3] = (ToggleButton) findViewById(R.id.toggleButton4);
        tButtons[4] = (ToggleButton) findViewById(R.id.toggleButton5);
        tButtons[5] = (ToggleButton) findViewById(R.id.toggleButton6);
        tButtons[6] = (ToggleButton) findViewById(R.id.toggleButton7);
        tButtons[7] = (ToggleButton) findViewById(R.id.toggleButton8);
        tButtons[8] = (ToggleButton) findViewById(R.id.toggleButton9);
        tButtons[9] = (ToggleButton) findViewById(R.id.toggleButton10);
        tButtons[10] = (ToggleButton) findViewById(R.id.toggleButton11);
        tButtons[11] = (ToggleButton) findViewById(R.id.toggleButton12);
        tButtons[12] = (ToggleButton) findViewById(R.id.toggleButton13);
        tButtons[13] = (ToggleButton) findViewById(R.id.toggleButton14);
        tButtons[14] = (ToggleButton) findViewById(R.id.toggleButton15);
        tButtons[15] = (ToggleButton) findViewById(R.id.toggleButton16);
        tButtons[16] = (ToggleButton) findViewById(R.id.toggleButton17);
        tButtons[17] = (ToggleButton) findViewById(R.id.toggleButton18);
        tButtons[18] = (ToggleButton) findViewById(R.id.toggleButton19);
        tButtons[19] = (ToggleButton) findViewById(R.id.toggleButton20);
        tButtons[20] = (ToggleButton) findViewById(R.id.toggleButton21);
        tButtons[21] = (ToggleButton) findViewById(R.id.toggleButton22);
        tButtons[22] = (ToggleButton) findViewById(R.id.toggleButton23);
        tButtons[23] = (ToggleButton) findViewById(R.id.toggleButton24);
        tButtons[24] = (ToggleButton) findViewById(R.id.toggleButton25);
        tButtons[25] = (ToggleButton) findViewById(R.id.toggleButton26);
        tButtons[26] = (ToggleButton) findViewById(R.id.toggleButton27);
        tButtons[27] = (ToggleButton) findViewById(R.id.toggleButton28);
        tButtons[28] = (ToggleButton) findViewById(R.id.toggleButton29);
        tButtons[29] = (ToggleButton) findViewById(R.id.toggleButton30);

        // Auto Mode ToggleButton 설정
        autoModeToggle = findViewById(R.id.autoModeToggle);
        autoModeToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isAutoMode = isChecked; // 자동 모드 활성화 여부 갱신
            if (isAutoMode) {
                startAutoMode();
            } else {
                stopAutoMode();
            }
        });
    }

    private void startAutoMode() {

        if (count == -1) {
            count = 0;
            initTTSS();
            initializeSequenceAndLessons();
        }

        autoModeRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isAutoMode || count >= maxCount) {
                    stopAutoMode(); // 자동 모드 중지 조건
                    return;
                }

                // Step 1: '-' 표시
                int currentIndex = sequence[count];
                String currentWord = lessons[currentIndex];
                textViewString.setText("-");
                startSpeak(currentWord);

                // Step 2: 글자 수만큼 대기 후 단어 표시
                int delayBeforeReveal = calculateDelay(currentWord.length());

                autoHandler.postDelayed(() -> {
                    if (!isAutoMode) {
                        stopAutoMode(); // 모드가 꺼져 있으면 중지
                        return;
                    }

                    // 단어 표시 및 진행
                    proceedLesson();
                    textViewString.setText(currentWord);

                    // Step 3: 단어 표시 후 5초 대기, 다음 단어로 진행
                    autoHandler.postDelayed(() -> {
                        if (isAutoMode) {
                            autoHandler.post(autoModeRunnable); // 다음 단어로 진행
                        }
                    }, 5000); // 단어 표시 후 5초 대기
                }, delayBeforeReveal); // 글자 수에 비례한 대기 시간
            }
        };

        autoHandler.post(autoModeRunnable); // 자동 모드 시작
    }


    private void stopAutoMode() {
        isAutoMode = false; // 자동 모드 상태를 false로 설정
        autoHandler.removeCallbacks(autoModeRunnable); // Handler에서 모든 예약 작업 제거
    }


    // 대기 시간 계산
    private int calculateDelay(int wordLength) {
        // 단어 길이를 최소 5, 최대 10으로 제한
        int adjustedLength = Math.max(5, Math.min(wordLength, 10));
        return adjustedLength * 700; // 조정된 단어 길이에 700ms 곱하기
    }


    private void initTTSS() {
        tts = new TextToSpeech(this, state -> {
            if (state == TextToSpeech.SUCCESS) {
                configureTTS();
                startSpeak("Shua Fighting");
            } else {
                Log.e("TTS", "Initialization failed!");
            }
        }, "com.google.android.tts");
    }

    private void configureTTS() {
        // TTS 설정
        tts.setLanguage(Locale.ENGLISH);
        tts.setSpeechRate(0.6f);

        // Voice 설정
        Set<String> features = new HashSet<>();
        features.add("male"); // 남성 목소리로 설정
        Voice voice = new Voice("en-us-x-sfg#male_1-local", Locale.US, 400, 200, false, features);
        tts.setVoice(voice);
    }

    private void startSpeak(String text) {
        if (text == null || text.isEmpty()) return; // 텍스트 유효성 검사
        lastMissedTTS = text;
        tts.speak(text, TextToSpeech.QUEUE_ADD, null, text);
    }

    public void initTTS(View view) {
        if (count == -1) {
            // 초기화 및 첫 실행
            initTTSS();
            initializeSequenceAndLessons();
            btnStart.setText("Start");
            count = 0;
        } else if (count == maxCount) {
            // 모든 학습 완료
            resetProgress();
        } else {
            // 학습 진행
            proceedLesson();
        }
    }

    private void initializeSequenceAndLessons() {
        wordCount = calculateWordCount();
        sequence = initializeSequence(wordCount);
        shuffleArray(sequence);

        lessons = new String[wordCount];
        lessonNumber = new int[wordCount];

        populateLessonsAndNumbers();
        maxCount = wordCount;
        seekbarProgress.setMax(maxCount);
    }

    private int calculateWordCount() {
        int count = 0;
        for (int i = 0; i < lessonNum; i++) {
            if (lessonSelect[i]) count += lessonss[i].length;
        }
        return count;
    }

    private int[] initializeSequence(int size) {
        int[] seq = new int[size];
        for (int i = 0; i < size; i++) seq[i] = i;
        return seq;
    }

    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private void populateLessonsAndNumbers() {
        int index = 0;
        for (int i = 0; i < lessonNum; i++) {
            if (lessonSelect[i]) {
                for (String word : lessonss[i]) {
                    lessons[index] = word;
                    lessonNumber[index++] = i;
                }
            }
        }
    }

    private void resetProgress() {
        seekbarProgress.setProgress(0);
        btnStart.setText("Start");
        count = 0;
    }

    private void proceedLesson() {
        btnRepeat.setEnabled(true);
        btnString.setEnabled(true);

        if (count > 0) tButtons[lessonNumber[sequence[count - 1]]].setChecked(false);

        int current = sequence[count];
        startSpeak(lessons[current]);

        highlightCurrentLesson(current);

        btnStart.setText(count == maxCount - 1 ? "Finish" : "Next");
        textViewProgress.setText((count + 1) + " / " + maxCount);
        textViewString.setText("-");
        seekbarProgress.setProgress(count);
        count++;
    }

    private void highlightCurrentLesson(int current) {
        int lessonIndex = lessonNumber[current];
        if (lessonIndex >= 0 && lessonIndex < lessonNum) {
            tButtons[lessonIndex].setChecked(true);
        }
    }

    public void onClickRepeat(View view) {
        // 현재 count에 해당하는 sequence 값을 가져와 직접 처리
        int currentIndex = sequence[count - 1]; // 마지막 사용된 count 값 기준
        startSpeak(lessons[currentIndex]);
    }

    public void showString(View view) {
        int currentIndex = sequence[count - 1]; // 마지막 사용된 count 값 기준
        String currentWord = lessons[currentIndex];

        textViewString.setText(currentWord);

        // 배열 번호와 단어를 추가 (중복 방지)
        int arrayNumber = lessonNumber[currentIndex];
        Pair<Integer, String> wordPair = new Pair<>(arrayNumber, currentWord);

        if (!shownWordPairs.contains(wordPair)) {
            shownWordPairs.add(wordPair);
        }

        // textViewString 클릭 시 팝업 표시
        textViewString.setOnClickListener(v -> showWordListPopup());
    }

    private void showWordListPopup() {
        if (shownWordPairs.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("단어 리스트")
                    .setMessage("아직 확인된 단어가 없습니다.")
                    .setPositiveButton("닫기", (dialog, which) -> dialog.dismiss())
                    .show();
        } else {
            // 정렬: 배열 번호 기준
            shownWordPairs.sort((p1, p2) -> Integer.compare(p1.first, p2.first));

            // 배열별로 단어 묶기
            StringBuilder wordsGrouped = new StringBuilder();
            int currentArray = -1;
            List<String> currentWords = new ArrayList<>();

            for (Pair<Integer, String> pair : shownWordPairs) {
                int arrayNumber = pair.first; // 배열 번호
                String word = pair.second;

                // 새로운 배열이면 기존 단어 그룹을 추가
                if (arrayNumber != currentArray) {
                    if (currentArray != -1) {
                        // 이전 그룹 출력
                        wordsGrouped.append(String.join(", ", currentWords)).append("\n");
                    }
                    // 새로운 그룹 시작
                    wordsGrouped.append(arrayNumber + 1).append(": ");
                    currentArray = arrayNumber;
                    currentWords.clear();
                }

                // 현재 그룹에 단어 추가
                currentWords.add(word);
            }

            // 마지막 그룹 추가
            if (!currentWords.isEmpty()) {
                wordsGrouped.append(String.join(", ", currentWords));
            }

            // 팝업 표시
            new AlertDialog.Builder(this)
                    .setTitle("확인된 단어 리스트")
                    .setMessage(wordsGrouped.toString())
                    .setPositiveButton("닫기", (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }
}