package com.minegusta.minegustarpg.npc;

import com.google.common.collect.Lists;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public enum DefaultNPC implements NPC {

    TOWN_GUARD(new DetailedNPC(DetailedNPC.Type.RANDOM, "Town Guard", Lists.newArrayList("These vampires are becoming a real menace.", "No lollygaggin'.", "Helgen... destroyed by a dragon. Hard to believe, isn't it?", "Wait... I know you.", "I used to be an adventurer like you. Then I took an arrow in the knee....", "Let me guess... someone stole your sweetroll.", "Citizen.", "Disrespect the law, you disrespect me.", "Trouble?"), false, null, null, null, null)),
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
    }, false, null, null, null, null)),
    MAGIC_STORE_OWNER(new DetailedNPC(DetailedNPC.Type.NORMAL, "Magic Store Owner", new ArrayList<String>() {
        {
            add("Hello there adventurer!");
            add("I am the Owner of this store... And I could use your help.");
            add("You see, I am running out of DeathWeed, which I use to bake cookies for pesky children.");
            add("I heard there is some DeathWeed in the dungeon of Azaran, which is relatively close.");
            add("Spiders have taken over though. If you are willing to take the risk to get me the DeathWeed, I would reward you.");
            add("Just click me with the DeathWeed when you have obtained it.");
            add("Good day lad!");
        }
    }, true, 31, 3, "DeathWeed", "Well done! Here is 3 points for the efford!"));

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
    public boolean isTrader() {
        return nPC.isTrader();
    }

    @Override
    public String getRewardMeta() {
        return nPC.getRewardMeta();
    }

    @Override
    public String getRewardMessage() {
        return nPC.getRewardMessage();
    }

    @Override
    public int getRewardPoints() {
        return nPC.getRewardPoints();
    }

    public int getRewardItem() {
        return nPC.getRewardItem();
    }

    @Override
    public boolean awardItem(Player player, LivingEntity villager) {
        return nPC.awardItem(player, villager);
    }

    @Override
    public Map<String, Object> serialize() {
        return nPC.serialize();
    }
}
