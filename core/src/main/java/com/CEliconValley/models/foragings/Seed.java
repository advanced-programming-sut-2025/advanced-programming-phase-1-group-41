package com.CEliconValley.models.foragings;

import com.CEliconValley.models.items.Item;

public class Seed implements Item {
    private final SeedType seedType;

    public Seed(SeedType seedType) {
        this.seedType = seedType;
    }

    @Override
    public String getChar() {
        return "SS";
    }

    @Override
    public String getName() {
        return seedType.getName();
    }

    public SeedType getSeedType() {
        return seedType;
    }

    @Override
    public double getPrice() {
        return seedType.getPrice();
    }
    public int getID() {
        switch (this.seedType) {
            case ApricotSapling -> { return 80000; }
            case CherrySapling -> { return 80001; }
            case BananaSapling -> { return 80002; }
            case MangoSapling -> { return 80003; }
            case OrangeSapling -> { return 80004; }
            case PeachSapling -> { return 80005; }
            case AppleSapling -> { return 80006; }
            case PomegranateSapling -> { return 80007; }

            case Acorns -> { return 80100; }
            case MapleSeeds -> { return 80101; }
            case PineCones -> { return 80102; }
            case MahoganySeeds -> { return 80103; }
            case MushroomTreeSeeds -> { return 80104; }
            case MysticTreeSeeds -> { return 80105; }

            case Mixed -> { return 80106; }
            case CauliflowerSeed -> { return 80107; }

            case ParsnipSeed -> { return 80200; }
            case PotatoSeed -> { return 80201; }
            case BlueJazzSeed -> { return 80202; }
            case TulipSeed -> { return 80203; }
            case CornSeed -> { return 80204; }
            case HotPepperSeed -> { return 80205; }
            case RadishSeed -> { return 80206; }
            case WheatSeed -> { return 80207; }

            case PoppySeed -> { return 80300; }
            case SunflowerSeed -> { return 80301; }
            case SummerSpangleSeed -> { return 80302; }
            case ArtichokeSeed -> { return 80303; }
            case EggplantSeed -> { return 80304; }
            case PumpkinSeed -> { return 80305; }
            case FairyRoseSeed -> { return 80306; }
            case PowdermelonSeed -> { return 80307; }

            case Jazz -> { return 80400; }
            case CarrotSeed -> { return 80401; }
            case CoffeeBeanSeed -> { return 80402; }
            case GarlicSeed -> { return 80403; }
            case Bean -> { return 80404; }
            case KaleSeed -> { return 80405; }
            case RhubarbSeed -> { return 80406; }
            case StrawberrySeed -> { return 80407; }

            case Rice -> { return 80500; }
            case BlueberrySeed -> { return 80501; }
            case HopsStarter -> { return 80502; }
            case Pepper -> { return 80503; }
            case MelonSeed -> { return 80504; }
            case RedCabbageSeed -> { return 80505; }
            case StarfruitSeed -> { return 80506; }
            case Spangle -> { return 80507; }

            case SummerSquashSeed -> { return 80600; }
            case TomatoSeed -> { return 80601; }
            case AmaranthSeed -> { return 80602; }
            case BeetSeed -> { return 80603; }
            case BokChoySeed -> { return 80604; }
            case BroccoliSeed -> { return 80605; }
            case CranberrySeed -> { return 80606; }
            case Fairy -> { return 80607; }

            case GrapeStarter -> { return 80700; }
            case Yam -> { return 80701; }
            case RareSeed -> { return 80702; }
            case AncientSeed -> { return 80703; }

            default -> throw new IllegalStateException("Unknown seed: " + this);
        }
    }



}
