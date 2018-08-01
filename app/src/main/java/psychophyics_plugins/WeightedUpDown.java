package psychophyics_plugins;

import java.util.Collections;
import java.util.List;

/**
 * Created by broly on 13/03/2017.
 */

public class WeightedUpDown {
int error_level_count=0,rem_level;
List<Integer> error_levels;
int chance = 2;
boolean final_level =false;

    public WeightedUpDown() {
    }

    public int calculateNextLevel(Boolean correct, int level) {
        final double threshold = 0.75;

        //

        if (correct == true) //
        {
            final_level = false;

            level = level + 1;
            error_level_count = 0;
        }
        else if(correct == false && error_level_count < 3) //
        {
            final_level =false;
            level = level + 1;
            error_level_count =error_level_count + 1 ;
        }
        else if(correct == false && error_level_count == 3 && chance==2 )
        {
            final_level =false;
            level = level-2;
            chance = 1;
        }
        else if(correct == false && error_level_count == 3 && chance==1 ) // Termination condition
        {
           final_level = true;
           level = level - 3;
        }
        else
        {

        }



        return level;
    }
}