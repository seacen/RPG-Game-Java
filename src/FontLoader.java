/* 433-294 Object Oriented Software Development
 * Slick font loader.
 * Makes it very easy to load font files into Slick Font objects.
 * Author: Matt Giuca <mgiuca>
 */

import java.io.File;

import org.newdawn.slick.Font;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.SlickException;

/** This class is provided to reduce the complexity of loading fonts.
 */
@SuppressWarnings("deprecation")
public class FontLoader
{
    /** Loads a font from a TTF file.
     * @param ttf_filename Path to a TTF font file to use to draw text.
     * @param textsize Font size, in points.
     * @return A font object. May be used with Graphics.setFont to set the
     *   default rendering font for the game.
     */
    public static Font loadFont(String ttf_filename, float textsize)
        throws SlickException
    {
        // First, load a java.awt.Font of the given TTF file
        java.awt.Font awtfont;
        try
        {
            awtfont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                new File(ttf_filename));
        }
        catch (java.io.IOException e)
        {
            throw new SlickException("Could not open font file "
                + ttf_filename);
        }
        catch (java.awt.FontFormatException e)
        {
            throw new SlickException("Could not load font " + ttf_filename);
        }
        // Create a "derived font" with the correct font size
        awtfont = awtfont.deriveFont(textsize);
        // Now wrap it in a Slick TrueTypeFont object (with anti-aliasing)
        return new TrueTypeFont(awtfont, true);
    }
}
