import org.junit.Test;

import sha256.Sha256;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class Sha256Test {

    @Test
    public void applyOneChunkV1() {
        try {
            assertEquals(
                    "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", 
                    Sha256.apply("")
            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void applyOneChunkV2() {
        try {
            assertEquals(
                    "7b31b48f1fb2b948436c72c6d95e182165555964bdd62190de302b31ead5f6ef", 
                    Sha256.apply("ojurhqgdsv28tg2h0b")
            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void applyOneChunkV3() {
        try {
            assertEquals(
                    "55475b7b710bea6ae076b5a07a151fde0549565f496071bed0affdffa25ef741", 
                    Sha256.apply("mikvzr0e8f2j01ty5gfbdv120ws58f41g02*/fwd+hb<+tj5,2 +9w")
            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void applyTwoChunksV1() {
        try {
            assertEquals(
                    "b832b8d0bb3d7bfa4ddf00832f27826edde3b08013190e3dc2ece78ad39b5b8e", 
                    Sha256.apply("mikvzr0e8f2j01ty5gfrzhbdv120ws58f41g02*dwfg/fwd+hb<+tqhj5,2 +rrwykl;sty,wfnd")
            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void applyTwoChunksV2() {
        try {
            assertEquals(
                    "3270cdbdc40f5d624bf090eb701d33332ef877bb2836e4afa6e091121d78e641", 
                    Sha256.apply("mikvzr0e8f2j01ty5gfrzhbd\"kd)09qe9rhbw_{}v120#ws58f41g02*dwfg/fwd+hb<+tqhj5,2 +rrwykl;sty,wfnd")
            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void applyManyChunksV1() {
        try {
            assertEquals(
                    "39368b4868be9a419162132c86d9a2bca06ac12975a0eccb09a321d6896be173", 
                    Sha256.apply("Y8D-HvT3*be#PWzt%j'QFyCAssDVL;v@t?!_r2O4]~)0ywa[4_Ry:.FD*AjGUhI5I49PA7\"gaLZy\"5?E:m?*3F*gp4FM%pb')$c$oe_l5%v(}dV\"$jb-$5&wN(X:0y-pH4_Om1!xH]L'\"M83k3Rs'0l)Y2!hKy(r2Az'~#LaowvnAisFG50aCz7+DSYV0g8Q_y'Y2D'9yB2+P")
            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void applyManyChunksV2() {
        try {
            assertEquals(
                    Sha256.apply("iUyys#B][u}GTxR%ëwV,Sç2v%/3Vùs5ê%hR{+A?2PxE-°kQzEGm2u~ü0Hî5|BAçyailw;lylXOl!ö°V#mFï:Mî5ùD8c}tùZEoX+~ùu8l )AnüèjBàS7M]G(1.&Wjt|-rxnN!U\"5PpuICâi@IyöU\"DBTX Yù)\"%WVh1tRwFU3Q*[1UXuwJigyOè__ONèçzP7£5qî5tA+Uy\"e*#anî$1AD/Ebëïh+{Y-B8kbcCcN~ê9hY°J'ùeJö47$üà,9k0.â}3K7$!ASJùzYTc@{2-LKQ4NPu3m6Yz$5&êFE8äâySK$FqOYJh%OoQWR.@9R2ê3.@TM]dyDx-TF£{ïv|î*T:6öF@qF(fsYWè:9èmâBcâyPxeY£çbsySFJLV;nâP)N\"UüM'cS{m%G9äVX6X°üry%rE4v%El8+(L4ug&hd3cîBc#,à°iEkùA°f\".9qv;0nF°l(OîCiQKoq°OPùVy{£ü,-üMâY[&U/+JO9°7O#)eMBZH@zZua:jf{a./â;èM]è%,??ï-x0_8KO xgPZüx°aye_u]81!97SnDîä//04_np/î)|à5);}35l&yMg.qE|âDê£A_:,èC}GNr}Qeâ%"), 
                    "b66d116b5f4c1e36db373660c8db36776b2fc45cc5a174311ea8943fb0155bab");
        } catch (Exception e) {
            fail();
        }
    }

}