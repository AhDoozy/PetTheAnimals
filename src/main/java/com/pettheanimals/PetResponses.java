package com.pettheanimals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class PetResponses
{
    public static final java.util.Set<String> WHITELISTED_NPCS = new java.util.HashSet<>(java.util.Arrays.asList(
        "Cat",
        "Kitten",
        "Hellcat",
        "Dog",
        "Stray dog",
        "Cow",
        "Calf",
        "Cow Calf",
        "Sheep",
        "Ram",
        "Chicken",
        "Duck",
        "Camel",
        "Yak",
        "Penguin",
        "Goat",
        "Horse",
        "Rabbit",
        "Seagull"
    ));

    private static final Map<String, java.util.List<String>> RESPONSES;
    static
    {
        Map<String, java.util.List<String>> m = new HashMap<>();
        m.put("cat", java.util.Arrays.asList(
            "You scratch behind the ears. Purrr.",
            "Gertrude would be proud. *Purrs in Varrock*",
            "The cat kneads the air like it owns a POH rug."
        ));
        m.put("hellcat", java.util.Arrays.asList(
            "Careful now—still deserves head pats.",
            "The hellcat purrs like a tiny demon engine.",
            "You pet the chaos, and the chaos purrs back."
        ));
        m.put("kitten", java.util.Arrays.asList(
            "Smol bean receives gentle boops.",
            "The kitten chases your cursor—uh, hand.",
            "The kitten mews; you gain +1 wholesome."
        ));
        m.put("dog", java.util.Arrays.asList(
            "Good pup! Tail wag intensifies.",
            "Much pet. Very wow. So Gielinor.",
            "The doggo does zoomies in your heart."
        ));
        m.put("sheep", java.util.Arrays.asList(
            "So fluffy. Baa-rilliant choice.",
            "Reminds you of that time in Lumbridge… baa.",
            "Wool you look at that—premium floof."
        ));
        m.put("cow", java.util.Arrays.asList(
            "Moo-tually appreciated head pats.",
            "The cow looks content and gives a gentle moo.",
            "Udderly wholesome. Farmer Fred approves."
        ));
        m.put("calf", java.util.Arrays.asList(
            "Tiny moo receives tiny pat.",
            "The calf bumps your hand, practicing headbutts.",
            "Future dairy royalty accepts your affection."
        ));
        m.put("chicken", java.util.Arrays.asList(
            "Cluck yeah—nice feathers.",
            "Colonel Who? This one's a friend.",
            "Lumbridge legend gets a gentle scritch."
        ));
        m.put("duck", java.util.Arrays.asList(
            "A very quacktical pet.",
            "You have been visited by the wholesome duck of luck.",
            "The duck waddles proudly—no bread required."
        ));
        m.put("camel", java.util.Arrays.asList(
            "Hump day happiness.",
            "Al Kharid royalty grants you one (1) smug grunt.",
            "You avoid the spit—*expert petter*."
        ));
        m.put("yak", java.util.Arrays.asList(
            "Yak shaved. Yak petted.",
            "Jatizso's fluff mountain accepts tribute.",
            "So tanky it probably has 99 Constitution."
        ));
        m.put("penguin", java.util.Arrays.asList(
            "Nothing to see here, comrade. *Waddle*",
            "You pet the spy. The spy pretends not to notice.",
            "Cold War flashbacks intensify, but it's adorable."
        ));
        m.put("goat", java.util.Arrays.asList(
            "Greatest Of All Time pets.",
            "Pollnivneach peckish pal enjoys the scritches.",
            "Headbutt restrained. Respect earned."
        ));
        m.put("horse", java.util.Arrays.asList(
            "A noble steed enjoys the attention.",
            "Still not rideable, still magnificent.",
            "Neigh-sayers be silent—this mane is majestic."
        ));
        m.put("rabbit", java.util.Arrays.asList(
            "Bun detected. Critical pet deployed.",
            "You gently boop the snoot. +10 serenity.",
            "The rabbit does a happy little thump."
        ));
        m.put("seagull", java.util.Arrays.asList(
            "Mine? Mine? Fine—pet.",
            "You pet the sky pirate; it spares your chips.",
            "A dignified squawk approves this interaction."
        ));
        RESPONSES = Collections.unmodifiableMap(m);
    }

    private PetResponses() { }

    public static String buildLine(String npcName, String customLines)
    {
        final String name = npcName == null ? "creature" : npcName;
        final String key = name.toLowerCase(Locale.ROOT);

        if (customLines != null && !customLines.trim().isEmpty())
        {
            java.util.List<String> userLines = new java.util.ArrayList<>();
            for (String line : customLines.split("[\\n,]"))
            {
                String trimmed = line.trim();
                if (!trimmed.isEmpty())
                {
                    userLines.add(trimmed);
                }
            }
            if (!userLines.isEmpty())
            {
                return userLines.get(new java.util.Random().nextInt(userLines.size()));
            }
        }

        for (Map.Entry<String, java.util.List<String>> e : RESPONSES.entrySet())
        {
            if (key.contains(e.getKey()))
            {
                java.util.List<String> responses = e.getValue();
                if (!responses.isEmpty()) {
                    return responses.get(new java.util.Random().nextInt(responses.size()));
                }
            }
        }
        return "You gently pet the " + name.toLowerCase(Locale.ROOT) + ".";
    }
}
