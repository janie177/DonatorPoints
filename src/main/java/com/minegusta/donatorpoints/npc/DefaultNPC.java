package com.minegusta.donatorpoints.npc;

import com.google.common.collect.Lists;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public enum DefaultNPC implements NPC {

    TOWN_GUARD(new DetailedNPC(DetailedNPC.Type.RANDOM, "Town Guard", Lists.newArrayList("These vampires are becoming a real menace.", "No lollygaggin'.", "Helgen... destroyed by a dragon. Hard to believe, isn't it?", "Wait... I know you.", "I used to be an adventurer like you. Then I took an arrow in the knee....", "Let me guess... someone stole your sweetroll.", "Citizen.", "Disrespect the law, you disrespect me.", "Trouble?"))),
    VETERAN_STINKY_JOE(new DetailedNPC(DetailedNPC.Type.NORMAL, "Veteran StinkyJoe", new ArrayList<String>() {
        {
            add("When I was young, nobody wore clothes.");
            add("But nowadays, you need to wear diamonds or something.");
            add("I was so confused about all of this that I had to figure out how to make my own diamond clothing.");
            add("Long story short:  I'm all out of money.");
            add("If you could please spare a few emeralds for a poor old man...");
            add("%pause%"); // wait
            add("Please?");
            add("%beat%");  // wait twice as long
            add("Oh, and check out /points because I was paid a few emeralds to say that.");
        }
    }));

    private DetailedNPC nPC;

    private DefaultNPC(DetailedNPC nPc) {
        this.nPC = nPc;
    }

    @Override
    public String getName() {
        return nPC.getName();
    }

    @Override
    public Collection<String> getDialog() {
        return nPC.getDialog();
    }

    @Override
    public void sendDialog(Collection<Player> players) {
        nPC.sendDialog(players);
    }

    @Override
    public boolean is(String name) {
        return nPC.is(name);
    }

    @Override
    public Map<String, Object> serialize() {
        return nPC.serialize();
    }
}
