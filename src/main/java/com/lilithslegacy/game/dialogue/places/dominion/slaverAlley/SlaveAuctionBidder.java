package com.lilithslegacy.game.dialogue.places.dominion.slaverAlley;

import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.race.AbstractSubspecies;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.utils.Util;

import java.util.List;

/**
 * @author Innoxia
 * @version 0.2.11
 * @since 0.1.90
 */
public class SlaveAuctionBidder {

    private final String name;
    private final AbstractSubspecies subspecies;
    private final Gender gender;
    private final List<String> biddingComments;
    private final List<String> failedBidComments;
    private final List<String> successfulBidComments;

    public SlaveAuctionBidder(AbstractSubspecies subspecies, Gender gender, List<String> biddingComments, List<String> failedBidComments, List<String> successfulBidComments) {
        super();
        this.subspecies = subspecies;
        this.gender = gender;
        this.biddingComments = biddingComments;
        this.failedBidComments = failedBidComments;
        this.successfulBidComments = successfulBidComments;

        if (gender.isFeminine()) {
            name = subspecies.getSingularFemaleName(null);
        } else {
            name = subspecies.getSingularMaleName(null);
        }
    }

    public static SlaveAuctionBidder generateNewSlaveAuctionBidder(NPC slave) {

        List<AbstractSubspecies> races = Util.newArrayListOfValues(
                Subspecies.CAT_MORPH,
                Subspecies.COW_MORPH,
                Subspecies.DEMON,
                Subspecies.DOG_MORPH,
                Subspecies.HARPY,
                Subspecies.HORSE_MORPH,
                Subspecies.HUMAN,
                Subspecies.SQUIRREL_MORPH,
                Subspecies.WOLF_MORPH);

        List<Gender> genders = Util.newArrayListOfValues(Gender.F_V_B_FEMALE, Gender.F_P_V_B_FUTANARI, Gender.M_P_MALE);

        AbstractSubspecies race = Util.randomItemFrom(races);
        Gender gender = Util.randomItemFrom(genders);

        List<String> biddingComments = Util.newArrayListOfValues(
                "I deserve a new fucktoy...",
                "My slaves need a new toy...",
                UtilText.parse(slave, "I could put [npc.herHim] to work in the brothel..."),
                UtilText.parse(slave, "I could put [npc.herHim] to work in the milking sheds..."),
                UtilText.parse(slave, "[npc.She] looks like [npc.she]'d make a good maid..."));

        List<String> failedBidComments = Util.newArrayListOfValues(
                "I can't afford that...",
                "That's too much for me...",
                "Maybe I'll bid on the next one...");

        List<String> successfulBidComments = Util.newArrayListOfValues(
                UtilText.parse(slave, "I'm going to break [npc.herHim] in as soon as I get home..."),
                UtilText.parse(slave, "I'll get my other slaves to break [npc.herHim] in..."),
                UtilText.parse(slave, "I'm sure [npc.she]'ll love [npc.her] new life in my brothel..."),
                UtilText.parse(slave, "I'm sure [npc.she]'ll love [npc.her] new life in the milking sheds..."));

        return new SlaveAuctionBidder(race, gender, biddingComments, failedBidComments, successfulBidComments);
    }

    public String getName(boolean withDeterminer) {
        if (withDeterminer) {
            return UtilText.generateSingularDeterminer(name) + " " + name;
        }
        return name;
    }

    public AbstractSubspecies getRace() {
        return subspecies;
    }

    public Gender getGender() {
        return gender;
    }

    public List<String> getBiddingComments() {
        return biddingComments;
    }

    public String getRandomBiddingComment() {
        return biddingComments.get(Util.random.nextInt(biddingComments.size()));
    }

    public List<String> getFailedBidComments() {
        return failedBidComments;
    }

    public String getRandomFailedBiddingComment() {
        return failedBidComments.get(Util.random.nextInt(failedBidComments.size()));
    }

    public List<String> getSuccessfulBidComments() {
        return successfulBidComments;
    }

    public String getRandomSuccessfulBiddingComment() {
        return successfulBidComments.get(Util.random.nextInt(successfulBidComments.size()));
    }
}
