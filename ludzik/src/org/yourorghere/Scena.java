package org.yourorghere;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;

public class Scena {

    static float z, x, kat;

    void Rysuj(GL gl, Texture t1, Texture t2, Texture t3) {
        
      
//szescian
        gl.glColor3f(1.0f, 1.0f, 1.0f);
//za�adowanie tekstury wczytanej wcze�niej z pliku krajobraz.bmp
        gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
        gl.glBegin(GL.GL_QUADS);
        
//�ciana przednia
        gl.glNormal3f(0.0f, 0.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(100.0f, -100.0f, 100.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-100.0f, -100.0f, 100.0f);
//�ciana tylnia
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(100.0f, 100.0f, -100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-100.0f, 100.0f, -100.0f);
//�ciana lewa
        gl.glNormal3f(1.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-100.0f, 100.0f, -100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-100.0f, -100.0f, 100.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-100.0f, -100.0f, -100.0f);
//�ciana prawa
        gl.glNormal3f(-1.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(100.0f, -100.0f, 100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(100.0f, 100.0f, -100.0f);
        gl.glEnd();

//�ciana dolna
//za�adowanie tekstury wczytanej wcze�niej z pliku niebo.bmp
        gl.glBindTexture(GL.GL_TEXTURE_2D, t2.getTextureObject());
        //ustawienia aby tekstura si� powiela�a
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glBegin(GL.GL_QUADS);
        gl.glNormal3f(0.0f, 1.0f, 0.0f);
        //koordynaty ustawienia 16 x 16 kwadrat�w powielonej tekstury na �cianie dolnej
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(100.0f, -100.0f, 100.0f);
        gl.glTexCoord2f(0.0f, 16.0f);
        gl.glVertex3f(100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(16.0f, 16.0f);
        gl.glVertex3f(-100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(16.0f, 0.0f);
        gl.glVertex3f(-100.0f, -100.0f, 100.0f);
        gl.glEnd();

        //�ciana gorna
//za�adowanie tekstury wczytanej wcze�niej z pliku trawa.bmp
        gl.glBindTexture(GL.GL_TEXTURE_2D, t3.getTextureObject());
        gl.glBegin(GL.GL_QUADS);
        gl.glNormal3f(0.0f, -1.0f, 0.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-100.0f, 100.0f, -100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(100.0f, 100.0f, -100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(100.0f, 100.0f, 100.0f);
        gl.glEnd();
    }

    void przesun(float d) {
        x -= d * Math.sin(kat * (3.14f / 180.0f));
        z += d * Math.cos(kat * (3.14f / 180.0f));
        System.out.println("z= "+z+" x= "+x);
       
                
    }

}
