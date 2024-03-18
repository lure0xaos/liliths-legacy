package com.lilithslegacy.game.dialogue;

import java.nio.file.Path;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lilithslegacy.utils.Util;

/**
 * Yes, this is horrible. I refactored it into this to easily allow modded flags.
 *
 * @author Innoxia
 * @version 0.4
 * @since 0.1.89
 */
public class DialogueFlagValue {

    // Main quest:
    public static final AbstractDialogueFlagValue firstReactionLiberate = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue firstReactionUsurp = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue firstReactionJoin = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue firstReactionNothing = new AbstractDialogueFlagValue();


    // Essence reactions:
    public static final AbstractDialogueFlagValue jinxedClothingDiscovered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue essencePostCombatDiscovered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue essenceOrgasmDiscovered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue essenceBottledDiscovered = new AbstractDialogueFlagValue();


    // Misc.:
    public static final AbstractDialogueFlagValue quickTrade = new AbstractDialogueFlagValue();
    /**
     * This is reset to false every time a transaction occurs, and should only be set to true in an NPC's applyItemTransactionEffects() method to prevent their default getTraderDescription() text from being displayed.
     */
    public static final AbstractDialogueFlagValue removeTraderDescription = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue stormTextUpdateRequired = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue hasSnowedThisWinter = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue foundHappiness = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue visitedSubmission = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue mommyFound = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue daddyFound = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue rudeToDaddy = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue flirtingWithDaddy = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue talkedWithDaddy = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue daddySendingReward = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue coveringChangeListenersRequired = new AbstractDialogueFlagValue(); // Set to false on every Response preparsing, and only set to true in getKatesDivCoveringsNew(). Used for setting up listeners in MainControllerInitMethod.

    public static final AbstractDialogueFlagValue badEnd = new AbstractDialogueFlagValue(); // When the game is in a state of a bad end (meaning that the player is in an inescapable gameplay loop)

    // Introductions:
    public static final AbstractDialogueFlagValue angelIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue angelsOfficeIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue bunnyIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue loppyIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ashleyIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ralphIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue nyanIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue nyanmumIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue kateIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue vickyIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue vanessaIntroduced = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue roxyIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue axelIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue eponaIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue elizabethIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue vengarIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue murkIntroduced = new AbstractDialogueFlagValue();


    // Red-light district:
    public static final AbstractDialogueFlagValue prostitutionLicenseObtained = new AbstractDialogueFlagValue();


    // City hall:
    public static final AbstractDialogueFlagValue cityHallLodgerBoardSeen = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue vanessaHelped = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue vanessaDailyHelped = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue vanessaDailyMassage = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue vanessaMassaged = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue vanessaFucked = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue vanessaAskedAboutCatalogue = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue vanessaAskedAboutSolitary = new AbstractDialogueFlagValue();


    // Shopping arcade:
    public static final AbstractDialogueFlagValue ralphAskedAboutHundredKisses = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ralphDailyBred = new AbstractDialogueFlagValue(true);

    public static final AbstractDialogueFlagValue ashleySexToysDiscovered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ashleyAttitude = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue reactedToKatePregnancy = new AbstractDialogueFlagValue();

    // Nyan:
    public static final AbstractDialogueFlagValue nyanHiding = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue nyanDating = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue nyanFirstKissed = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue nyanRestaurantDateRequested = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue nyanRestaurantDateCompleted = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue nyanmumInterviewPassed = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue nyanmumDateCompleted = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue nyanWeekendDated = new AbstractDialogueFlagValue(); // Reset every Monday in Nyan's dailyUpdate() method
    public static final AbstractDialogueFlagValue nyanmumGirlfriend = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue nyanApologised = new AbstractDialogueFlagValue(); // Apologise for her mum's behaviour
    public static final AbstractDialogueFlagValue nyanCreampied = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue nyanmumCreampied = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue nyanAnalTalk = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue nyanmumAnalTalk = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue nyanTalkedTo = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue nyanComplimented = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue nyanFlirtedWith = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue nyanHeadPatted = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue nyanKissed = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue nyanTummyRubbed = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue nyanWalked = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue nyanMakeOut = new AbstractDialogueFlagValue(true);
    public static AbstractDialogueFlagValue nyanSex = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue nyanGift = new AbstractDialogueFlagValue(true);

    // Lilaya's Home:
    public static final AbstractDialogueFlagValue knowsDate = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue lilayaDateTalk = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue auntHomeJustEntered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue hadSexWithLilaya = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue lilayaCondomBroke = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue lilayaAmazonsSecretImpregnation = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue reactedToPregnancyLilaya = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue waitingOnLilayaPregnancyResults = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue waitingOnLilayaBirthNews = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue essenceExtractionKnown = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue roseToldOnYou = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue lilayaReactedToPlayerAsDemon = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue lilayaHug = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue obtainedScientistClothing = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue lilayaGardenPickRose = new AbstractDialogueFlagValue(true);

    public static final AbstractDialogueFlagValue readBook1 = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue readBook2 = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue readBook3 = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue readBook4 = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue readBookSlavery = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue arthursPackageObtained = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue givenLilayaPresent1 = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue givenLilayaPresent2 = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue givenLilayaPresent3 = new AbstractDialogueFlagValue();

    // Enforcer HQ:
    public static final AbstractDialogueFlagValue braxEncountered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue accessToEnforcerHQ = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue braxTransformedPlayer = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue seenBraxAfterQuest = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue feminisedBrax = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue bimbofiedBrax = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue candiHarpyTransformation = new AbstractDialogueFlagValue();

    // Harpy Nests:
    public static final AbstractDialogueFlagValue hasHarpyNestAccess = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue bimboEncountered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue bimboPacified = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue dominantEncountered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue dominantPacified = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue nymphoEncountered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue nymphoPacified = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue punishedByHelena = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue scarlettRelaxed = new AbstractDialogueFlagValue(true);

    // Slaver Alley:
    public static final AbstractDialogueFlagValue finchIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue finchFreedomTalk = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue seanIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue seanSeenBrax = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue statueTruthRevealed = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slaverAlleyTalked = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slaverAlleyTalkedBraxReveal = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slaverAlleyTalkedFreedSlaves = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slaverAlleyComplained = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slaverAlleyVisitedHiddenAlleyway = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slaverAlleyBribed = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slaverAlleyTookPlace = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slaverAlleyCompanionInStocks = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slaverAlleyAcceptedDeal = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slaverAlleyCompanionAcceptedDeal = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slaverAlleyTwoPartners = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slaverAlleySlavesFreed = new AbstractDialogueFlagValue(); // Reset every day at midnight (as part of stocks reset method)

    public static final AbstractDialogueFlagValue slaverAlleyCafe1Visited = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue slaverAlleyCafe1Demonstrated = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue slaverAlleyCafe1DailyDemonstrated = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue slaverAlleyCafe2Visited = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue slaverAlleyCafe2Demonstrated = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue slaverAlleyCafe2DailyDemonstrated = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue slaverAlleyCafe3Visited = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue slaverAlleyCafe3Demonstrated = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue slaverAlleyCafe3DailyDemonstrated = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue slaverAlleyCafe4Visited = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue slaverAlleyCafe4Demonstrated = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue slaverAlleyCafe4DailyDemonstrated = new AbstractDialogueFlagValue(true);

    // Wes:
    public static final AbstractDialogueFlagValue wesQuestLilayaHelp = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue wesQuestMet = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue wesQuestRefused = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue wesQuestTalked = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue wesQuestTalkedAlt = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue wesQuestFlirted = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue wesQuestSex = new AbstractDialogueFlagValue(true);

    // Helena (romance quest):
    public static final AbstractDialogueFlagValue helenaCheapPaint = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue helenaGoneHome = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue scarlettGoneHome = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue helenaScarlettToldToReturn = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue helenaScarlettSleepoverSex = new AbstractDialogueFlagValue();

    // Helena (post-romance quest):
    public static final AbstractDialogueFlagValue helenaSlutSeen = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue helenaShopTalkedTo = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue helenaShopFucked = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue helenaNestTalkedTo = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue helenaNestFucked = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue helenaShopScarlettTalkedTo = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue helenaShopScarlettCounterOral = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue helenaShopScarlettCafe = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue helenaShopScarlettCafeRevealed = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue helenaShopScarlettExtraTransformationDiscussed = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue helenaShopScarlettExtraTransformationApplied = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue helenaShopScarlettExtraTransformationHelenaReacted = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue helenaDateApartmentSeen = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue helenaDateFirstDateComplete = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue helenaDateRomanticSetup = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue helenaDateRomanticSetupEatenOut = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue helenaGift = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue helenaDateSexLifeTalk = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue helenaDateVirginityTalk = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue helenaScarlettThreesome = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue helenaBedroomFromNest = new AbstractDialogueFlagValue();

    // Natalya:
    public static final AbstractDialogueFlagValue playerSubmittedToNatalya = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue natalyaDemandedFacial = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue playerReceivedNatalyaFacial = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue natalyaVisited = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue natalyaInterviewOffered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue natalyaBusy = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue natalyaDailySexAsSub = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue natalyaDailySexAsDom = new AbstractDialogueFlagValue(true);

    public static final AbstractDialogueFlagValue natalyaParkEncounter = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue playerSubmittedToNatalyaInPark = new AbstractDialogueFlagValue();


    // Kay:
    public static final AbstractDialogueFlagValue kayTalkedTo = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue kayFlirtedWith = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue kaySubmitted = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue kayPreviouslyFeminised = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue kayFeminised = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue kayDommed = new AbstractDialogueFlagValue(true);

    public static final AbstractDialogueFlagValue kayCratesSearched = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue dobermannDefeatPaid = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue dobermannDefeatEnforcer = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue dobermannDefeatDemon = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue dobermannDefeatSeduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue dobermannDefeatCombat = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue supplierDepotDoorUnlocked = new AbstractDialogueFlagValue(); // Named 'suppliers' from old quest structure, refers to dobermanns
    public static final AbstractDialogueFlagValue suppliersEncountered = new AbstractDialogueFlagValue();

    // Zaranix:
    public static final AbstractDialogueFlagValue zaranixDiscoveredHome = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue zaranixMaidsHostile = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue zaranixKnockedOnDoor = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue zaranixKickedDownDoor = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue zaranixAmberSubdued = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue zaranixKatherineSubdued = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue zaranixKellySubdued = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue zaranixTransformedPlayer = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue amberSatOnFloor = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue amberRepeatEncountered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue katherineRepeatEncountered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue kellyRepeatEncountered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue zaranixRepeatEncountered = new AbstractDialogueFlagValue();

    // Lumi:
    public static final AbstractDialogueFlagValue lumiMet = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue lumiDisabled = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue lumiPromisedDinner = new AbstractDialogueFlagValue();

    // Slime Queen's Tower:
    public static final AbstractDialogueFlagValue slimeGuardsIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slimeGuardsBluffed = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slimeGuardsDefeated = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slimeRoyalGuardIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slimeRoyalGuardDefeated = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slimeRoyalGuardDefeatReacted = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slimeQueenHelped = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slimeQueenConvinced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue slimeQueenForced = new AbstractDialogueFlagValue();

    // Gambling Den:
    public static final AbstractDialogueFlagValue axelMentionedVengar = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue axelExplainedVengar = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue axelToldSubmit = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue axelSissified = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue axelFeminised = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue axelClothingFeminine = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue axelClothingWhore = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue axelClothingMaid = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue roxyAddicted = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue roxyVengarOwnerIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue playedPregnancyRouletteAsMother = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue playedPregnancyRouletteAsBreeder = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue eponaMurkOwnerIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue eponaMurkSeen = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue eponaMurkSubmitted = new AbstractDialogueFlagValue();

    // Nightlife:
    public static final AbstractDialogueFlagValue julesIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue suckedJulesCock = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue fuckedJules = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue fuckedJulesTonight = new AbstractDialogueFlagValue() {
        @Override
        public int getResetHour() {
            return 12;
        }
    };

    public static final AbstractDialogueFlagValue passedJules = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue kalahariIntroduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue kalahariWantsSex = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue krugerIntroduced = new AbstractDialogueFlagValue();

    // Submission:
    public static final AbstractDialogueFlagValue claireAskedTeleportation = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue claireWarning = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue clairePadsInvestigated = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue claireEnclosureEscaped = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue claireObtainedLightningGlobe = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue claireAskedWarehouseEscape = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue impCitadelEncountered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impCitadelArcanistEncountered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impCitadelArcanistAcceptedTF = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impCitadelTreasurySearched = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impCitadelLaboratorySearched = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue impCitadelPrisonerMale = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impCitadelPrisonerFemale = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impCitadelPrisonerAlpha = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue impFortressAlphaGuardsPacified = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impFortressAlphaGuardsKnowPlayerDemon = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impFortressAlphaBossEncountered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impFortressAlphaPacified = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impFortressAlphaDefeated = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue impFortressFemalesGuardsPacified = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impFortressFemalesGuardsKnowPlayerDemon = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impFortressFemalesBossEncountered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impFortressFemalesPacified = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impFortressFemalesDefeated = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue impFortressMalesGuardsPacified = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impFortressMalesGuardsKnowPlayerDemon = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impFortressMalesBossEncountered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impFortressMalesPacified = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impFortressMalesDefeated = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue impFortressDemonBossEncountered = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impFortressDemonDefeated = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue impFortressDemonImpsDefeated = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue elizabethAskedAboutUniforms = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue elizabethAskedAboutSurname = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue elizabethAskedAboutRoutine = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue lyssiethQuestionAsked1 = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue lyssiethQuestionAsked2 = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue lyssiethQuestionAsked3 = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue lyssiethQuestionAsked4 = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue lyssiethQuestionAsked5 = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue lyssiethNoCockDemonTF = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue meraxisRepeatDemonTF = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue vendingMachineTalked = new AbstractDialogueFlagValue();


    // Rebel base:

    public static final AbstractDialogueFlagValue rebelBaseDarkPassFound = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue rebelBaseLightPassFound = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue rebelBaseInsaneSurvivorEncountered = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue rebelBaseElleCostumeEncountered = new AbstractDialogueFlagValue();


    // Rat warrens:

    public static final AbstractDialogueFlagValue ratWarrensEntry = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensEntryWhore = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensHostile = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensEntranceGuardsFight = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensSeenMilkers = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensMilkersBackground = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensMilkersFreeAttempt = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensSilenceIntroduced = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue ratWarrensClearedLeft = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensClearedCentre = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensClearedRight = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue ratWarrensLootedDiceDen = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue vengarThreatened = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue vengarPersuaded = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue vengarSeduced = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensUsedResonanceStone = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue vengarCaptiveRoomCleaned = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue vengarCaptiveVengarSatisfied = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue vengarCaptiveShadowSatisfied = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue vengarCaptiveSilenceSatisfied = new AbstractDialogueFlagValue(true);
    public static AbstractDialogueFlagValue vengarCaptiveCompanionGivenBirth = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue vengarCaptiveGangBanged = new AbstractDialogueFlagValue(true);

    public static final AbstractDialogueFlagValue ratWarrensCaptiveInitialNightDescription = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensCaptiveAttemptingEscape = new AbstractDialogueFlagValue();
    public static AbstractDialogueFlagValue ratWarrensCaptiveEscaped = new AbstractDialogueFlagValue();
//	public static AbstractDialogueFlagValue ratWarrensCaptiveTransformationsStarted = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue ratWarrensCaptiveFeminine = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensCaptiveFuta = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensCaptiveMasculine = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensCaptiveSissy = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue murkCaptiveBlowjob = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue murkMaster = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue ratWarrensCaptiveCalledOut = new AbstractDialogueFlagValue();
    public static final AbstractDialogueFlagValue ratWarrensCaptiveWashed = new AbstractDialogueFlagValue();

    public static AbstractDialogueFlagValue ratWarrensCaptiveCompanionGivenBirth = new AbstractDialogueFlagValue(true);
    public static AbstractDialogueFlagValue ratWarrensCaptiveOwnerSex = new AbstractDialogueFlagValue(true);
    public static AbstractDialogueFlagValue ratWarrensCaptiveOwnerCompanionSex = new AbstractDialogueFlagValue(true);
    public static AbstractDialogueFlagValue ratWarrensCaptiveDailyTransformed = new AbstractDialogueFlagValue(true);

    public static final AbstractDialogueFlagValue murkLectured = new AbstractDialogueFlagValue(true);
    public static final AbstractDialogueFlagValue murkSpanked = new AbstractDialogueFlagValue(true);

    public static final AbstractDialogueFlagValue milkersClaireDialogue = new AbstractDialogueFlagValue(false);


    //Felicia
    public static final AbstractDialogueFlagValue feliciaAskedArthurPersonality = new AbstractDialogueFlagValue(false);
    public static final AbstractDialogueFlagValue feliciaAskedArthurHobbies = new AbstractDialogueFlagValue(false);
    public static final AbstractDialogueFlagValue feliciaAskedAboutHerSurname = new AbstractDialogueFlagValue(false);
    public static final AbstractDialogueFlagValue feliciaAskedAboutHerPlace = new AbstractDialogueFlagValue(false);
    public static final AbstractDialogueFlagValue feliciaAskedAboutHerFur = new AbstractDialogueFlagValue(false);
    public static final AbstractDialogueFlagValue feliciaAskedAboutHerFavoriteStore = new AbstractDialogueFlagValue(false);
    public static final AbstractDialogueFlagValue feliciaToldAboutArthur = new AbstractDialogueFlagValue(false);
    public static final AbstractDialogueFlagValue feliciaLewdTalkAborted = new AbstractDialogueFlagValue(false);
    public static final AbstractDialogueFlagValue feliciaRejectedPlayer = new AbstractDialogueFlagValue(false);


    // Fields area:

    public static final AbstractDialogueFlagValue leftDominionFirstTime = new AbstractDialogueFlagValue();

    public static AbstractDialogueFlagValue centaurTransportEncountered = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue lunetteTerrorEnded = new AbstractDialogueFlagValue();

    public static final AbstractDialogueFlagValue minotallys_tf_required = new AbstractDialogueFlagValue();

    // ------------------------------------------------------------------------------------------------------------//

    // Utility methods for loading in external values:

    public static final List<AbstractDialogueFlagValue> allDialogueFlagValues;

    public static final Map<AbstractDialogueFlagValue, String> dialogueFlagValueToIdMap = new HashMap<>();
    public static final Map<String, AbstractDialogueFlagValue> idToDialogueFlagValueMap = new HashMap<>();


    /**
     * @param id Will be in the format of: 'innoxia_elis_berries_found'.
     * @return The flag that has an id closest to the supplied id. <b>Will return null</b> if the matching distance is greater than 3 (which typically will be more than enough to catch spelling errors, indicating that the flag has been removed).
     */
    public static AbstractDialogueFlagValue getDialogueFlagValueFromId(String id) {

//		public static AbstractDialogueFlagValue gymIntroduced = new AbstractDialogueFlagValue();
//		public static AbstractDialogueFlagValue gymHadTour = new AbstractDialogueFlagValue();
//		public static AbstractDialogueFlagValue gymIsMember = new AbstractDialogueFlagValue();
        // Removed flags:
        if (id.equals("ratWarrensRaid")
                || id.equals("suppliersTriedConvincing")
                // Reset gym flags so that the new gym starts out as a fresh start for versions loaded from prior to 0.4.7.8:
                || id.equals("gymIsMember")
                || id.equals("gymIntroduced")
                || id.equals("gymHadTour")) {
            return null;
        }

        if (id.equals("innoxia_elis_alleyway_transformations_applied")) {
            id = "innoxia_alleyway_transformations_applied";
        }
//		if(id.equals("gymIntroduced")) {
//			id = "innoxia_pix_introduced";
//		}
//		if(id.equals("gymHadTour")) {
//			id = "innoxia_pix_had_tour";
//		}

        id = Util.getClosestStringMatch(id, idToDialogueFlagValueMap.keySet(), 3);

        return idToDialogueFlagValueMap.get(id);
    }

    public static String getIdFromDialogueFlagValue(AbstractDialogueFlagValue perk) {
        return dialogueFlagValueToIdMap.get(perk);
    }

    public static List<AbstractDialogueFlagValue> getAllDialogueFlagValues() {
        return allDialogueFlagValues;
    }

    static {
        allDialogueFlagValues = new ArrayList<>();

        // Modded dialogueFlagValues:

        Map<String, Map<String, Path>> moddedFilesMap = Util.getExternalModFilesById("/dialogue", null, "flags");
        for (Entry<String, Map<String, Path>> entry : moddedFilesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                try {
                    List<AbstractDialogueFlagValue> dialogueFlagValues = AbstractDialogueFlagValue.loadFlagsFromFile(innerEntry.getValue(), entry.getKey(), true);

                    for (AbstractDialogueFlagValue loadedFlag : dialogueFlagValues) {
                        allDialogueFlagValues.add(loadedFlag);
                        dialogueFlagValueToIdMap.put(loadedFlag, loadedFlag.getId());
                        idToDialogueFlagValueMap.put(loadedFlag.getId(), loadedFlag);
//						System.out.println("modded DFV: "+innerEntry.getKey()+" "+loadedFlag.getId());
                    }
                } catch (Exception ex) {
                    System.getLogger("").log(System.Logger.Level.ERROR, "Loading modded dialogueFlagValue failed at 'DialogueFlagValue'. File path: " + innerEntry.getValue());
                    System.getLogger("").log(System.Logger.Level.ERROR, "Actual exception: ");
                    ex.printStackTrace(System.err);
                }
            }
        }

        // External res dialogueFlagValues:

        Map<String, Map<String, Path>> filesMap = Util.getExternalFilesById("/res/dialogue", null, "flags");
        for (Entry<String, Map<String, Path>> entry : filesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                try {
                    List<AbstractDialogueFlagValue> dialogueFlagValues = AbstractDialogueFlagValue.loadFlagsFromFile(innerEntry.getValue(), entry.getKey(), false);

                    for (AbstractDialogueFlagValue loadedFlag : dialogueFlagValues) {
                        allDialogueFlagValues.add(loadedFlag);
                        dialogueFlagValueToIdMap.put(loadedFlag, loadedFlag.getId());
                        idToDialogueFlagValueMap.put(loadedFlag.getId(), loadedFlag);
//						System.out.println("res DFV: "+innerEntry.getKey());
                    }
                } catch (Exception ex) {
                    System.getLogger("").log(System.Logger.Level.ERROR, "Loading dialogueFlagValue failed at 'DialogueFlagValue'. File path: " + innerEntry.getValue());
                    System.getLogger("").log(System.Logger.Level.ERROR, "Actual exception: ");
                    ex.printStackTrace(System.err);
                }
            }
        }

        // Hard-coded dialogueFlagValues (all those up above):

        Field[] fields = DialogueFlagValue.class.getFields();

        for (Field f : fields) {
            if (AbstractDialogueFlagValue.class.isAssignableFrom(f.getType())) {

                AbstractDialogueFlagValue dialogueFlagValue;

                try {
                    dialogueFlagValue = ((AbstractDialogueFlagValue) f.get(null));

                    String id = f.getName();
                    dialogueFlagValueToIdMap.put(dialogueFlagValue, id);
                    dialogueFlagValue.setId(id);
                    idToDialogueFlagValueMap.put(id, dialogueFlagValue);
                    allDialogueFlagValues.add(dialogueFlagValue);

                } catch (IllegalArgumentException | IllegalAccessException e) {
                    System.getLogger("").log(System.Logger.Level.ERROR, e.getMessage(), e);
                }
            }
        }
    }
}
