package org.yourorghere;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import javax.swing.JOptionPane;


public class Ludzik implements GLEventListener {

//statyczne pola okreœlaj¹ce rotacjê wokó³ osi X,Y,Z
    private static float xrot = 0.0f, yrot = 0.0f, zrot= 0.0f;
    public static float cx=0.0f, cy=0.0f, cz=-1.0f;
    private static int startList;
    private static int lewareka = 0, prawareka =0, lewanoga=0, prawanoga=0, glowa=0;
    public static Ludzik ludzik = new Ludzik();
    public static Scena scena = new Scena();
    static BufferedImage image1 = null, image2 = null, image3 = null;
    static Texture t1 = null, t2 = null, t3 = null;
    
    
    float kat1 = 0.0f;
    float kat3 = 45.0f;
    float kat5 = -45.0f;
    float kat7 = -45.0f;

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Ludzik());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        
        //Obs³uga klawiszy strza³ek, W, S, 1, 2, 3, 4, 5, 6, 7, 8, 9
        frame.addKeyListener(new KeyListener()
        {
        public void keyPressed(KeyEvent e)
        {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            xrot -= 1.0f;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            xrot += 1.0f;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            scena.kat+=1.5f;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            scena.kat-=1.5f;
        }
                 
                
        if (e.getKeyCode() == KeyEvent.VK_W) {
            if(scena.x>=-98 && scena.x<=98 &&scena.z>=-98 && scena.z<=98)
                scena.przesun(-1.0f);     
        }
                
        if (e.getKeyCode() == KeyEvent.VK_S) {
            if(scena.x>=-99f && scena.x<=99f &&scena.z>=-99f && scena.z<=99f)
                scena.przesun(1.0f);
        }
        
        if (e.getKeyChar() == '1')
            lewanoga++;
         
        if (e.getKeyChar() == '2')
            lewanoga--;
         
        if (e.getKeyChar() == '3')
            prawanoga++;
         
        if (e.getKeyChar() == '4')
            prawanoga--;
         
        if (e.getKeyChar() == '5')
            lewareka++;
         
        if (e.getKeyChar() == '6')
            lewareka--;
         
        if (e.getKeyChar() == '7')
            prawareka++;
         
        if (e.getKeyChar() == '8')
            prawareka--;
         
        if (e.getKeyChar() == '9')
            glowa++;        
         
        } 
         
        public void keyReleased(KeyEvent e){}
        public void keyTyped(KeyEvent e){}
        });

        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
        }


    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));      
        
        GL gl = drawable.getGL(); 
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
        
        //wy³¹czenie wewnêtrzych stron prymitywów
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glEnable(GL.GL_DEPTH_TEST);
         
         //wartoœci sk³adowe oœwietlenia i koordynaty Ÿród³a œwiat³a
        float ambientLight[] = { 0.3f, 0.3f, 0.3f, 1.0f };
        //swiat³o otaczaj¹ce
        float diffuseLight[] = { 0.7f, 0.7f, 0.7f, 1.0f };
        //œwiat³o rozproszone
        float specular[] = { 1.0f, 1.0f, 1.0f, 1.0f};
         //œwiat³o odbite
        float lightPos[] = { 0.0f, 150.0f, 150.0f, 1.0f };
        //pozycja œwiat³a
        //(czwarty parametr okreœla odleg³oœæ Ÿród³a:
        //0.0f-nieskoñczona; 1.0f-okreœlona przez pozosta³e parametry)
        gl.glEnable(GL/*2*/.GL_LIGHTING);
         //uaktywnienie oœwietlenia
        //ustawienie parametrów Ÿród³a œwiat³a nr. 0
        gl.glLightfv(GL/*2*/.GL_LIGHT0,GL/*2*/.GL_AMBIENT,ambientLight,0);
         //swiat³o otaczaj¹ce
        gl.glLightfv(GL/*2*/.GL_LIGHT0,GL/*2*/.GL_DIFFUSE,diffuseLight,0);
         //œwiat³o rozproszone
        gl.glLightfv(GL/*2*/.GL_LIGHT0,GL/*2*/.GL_SPECULAR,specular,0);
         //œwiat³o odbite
        gl.glLightfv(GL/*2*/.GL_LIGHT0,GL/*2*/.GL_POSITION,lightPos,0);
         //pozycja œwiat³a
        gl.glEnable(GL/*2*/.GL_LIGHT0);
         //uaktywnienie Ÿród³a œwiat³a nr. 0
        gl.glEnable(GL/*2*/.GL_COLOR_MATERIAL);
         //uaktywnienie œledzenia kolorów
        //kolory bêd¹ ustalane za pomoc¹ glColor
        gl.glColorMaterial(GL.GL_FRONT, GL/*2*/.GL_AMBIENT_AND_DIFFUSE);
        //Ustawienie jasnoœci i odblaskowoœci obiektów
        float specref[] =  { 1.0f, 1.0f, 1.0f, 1.0f }; 
        //parametry odblaskowoœci
        gl.glMaterialfv(GL/*2*/.GL_FRONT, GL/*2*/.GL_SPECULAR,specref,0);
        gl.glMateriali(GL/*2*/.GL_FRONT,GL/*2*/.GL_SHININESS,128);
        
    
        gl.glEnable(GL.GL_DEPTH_TEST);
        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.

        try {
            image1 = ImageIO.read(new File("krajobraz.bmp"));
            image2 = ImageIO.read(new File("trawa.bmp"));
            image3 = ImageIO.read(new File("niebo.bmp"));
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, exc.toString());
            return;
        }

        t1 = TextureIO.newTexture(image1, false);
        t2 = TextureIO.newTexture(image2, false);
        t3 = TextureIO.newTexture(image3, false);

        gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_BLEND | GL.GL_MODULATE);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
         
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        
        
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(150.0f, h, 0.2f, 300.0f); //transformacja perspektywiczna              
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

    }
        
       int cos = 0;
    public static float qwrot = 60.0f, asrot = -45.0f, zxrot = -45.0f,
            obrot = 0.0f, uprot = 0.0f, downrot = 0.0f;

    void Walec(GL gl, float promien, float dlugosc, float px, float py, float pz) {
        float x = 0.0f, y = 0.0f, kat = 0.0f;
        gl.glBegin(GL.GL_QUAD_STRIP);
        for (kat = 0.0f; kat < (2.0f * Math.PI); kat += (Math.PI / 32.0f)) {
            x = px + promien * (float) Math.sin(kat);
            y = py + promien * (float) Math.cos(kat);
            gl.glNormal3f((float) Math.sin(kat), (float) Math.cos(kat), 0.0f);
            gl.glVertex3f(x, y, pz);
            gl.glVertex3f(x, y, pz + dlugosc);
        }
        gl.glEnd();
        gl.glNormal3f(0.0f, 0.0f, -1.0f);
        x = y = kat = 0.0f;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(px, py, pz); //srodek kola
        for (kat = 0.0f; kat < (2.0f * Math.PI); kat += (Math.PI / 32.0f)) {
            x = px + promien * (float) Math.sin(kat);
            y = py + promien * (float) Math.cos(kat);
            gl.glVertex3f(x, y, pz);
        }
        gl.glEnd();
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        x = y = kat = 0.0f;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(px, py, pz + dlugosc); //srodek kola
        for (kat = 2.0f * (float) Math.PI; kat > 0.0f; kat -= (Math.PI / 32.0f)) {
            x = px + promien * (float) Math.sin(kat);
            y = py + promien * (float) Math.cos(kat);
            gl.glVertex3f(x, y, pz + dlugosc);
        }
        gl.glEnd();
    }

        void walec2(GL gl) {
//wywo³ujemy automatyczne normalizowanie normalnych
//bo operacja skalowania je zniekszta³ci
        gl.glEnable(GL.GL_NORMALIZE);

        float x, y, kat;
        gl.glColor3f(1.5f, 0.75f, 0.0f);
        gl.glBegin(GL.GL_QUAD_STRIP);
        for (kat = 0.0f; kat < (2.0f * Math.PI); kat += (Math.PI / 32.0f)) {
            x = 0.5f * (float) Math.sin(kat);
            y = 0.5f * (float) Math.cos(kat);
            gl.glNormal3f((float) Math.sin(kat), (float) Math.cos(kat), 0.0f);
            gl.glVertex3f(x, y, -1.0f);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
        gl.glNormal3f(0.0f, 0.0f, -1.0f);
        x = y = kat = 0.0f;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(0.0f, 0.0f, -1.0f); //srodek kola
        for (kat = 0.0f; kat < (2.0f * Math.PI); kat += (Math.PI / 32.0f)) {
            x = 0.5f * (float) Math.sin(kat);
            y = 0.5f * (float) Math.cos(kat);
            gl.glVertex3f(x, y, -1.0f);
        }
        gl.glEnd();
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        x = y = kat = 0.0f;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(0.0f, 0.0f, 0.0f); //srodek kola
        for (kat = 2.0f * (float) Math.PI; kat > 0.0f; kat -= (Math.PI / 32.0f)) {
            x = 0.5f * (float) Math.sin(kat);
            y = 0.5f * (float) Math.cos(kat);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
    }
        
        void stozek(GL gl) {
//wywo³ujemy automatyczne normalizowanie normalnych
        gl.glEnable(GL.GL_NORMALIZE);
        float x, y, kat;
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(0.0f, 0.0f, -2.0f); //wierzcholek stozka
        for (kat = 0.0f; kat < (4.0f * Math.PI); kat += (Math.PI / 32.0f)) {
            x = (float) Math.sin(kat);
            y = (float) Math.cos(kat);
            gl.glNormal3f((float) Math.sin(kat), (float) Math.cos(kat), -2.0f);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f); //srodek kola
        for (kat = 2.0f * (float) Math.PI; kat > 0.0f; kat -= (Math.PI / 32.0f)) {
            x = (float) Math.sin(kat);
            y = (float) Math.cos(kat);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
    }

    
    
    void ludzik(GL gl){
         gl.glColor3f(1, 1, 1);
         GLU glu = new GLU();
         GLUquadric qobj;
         GLUquadric qobj2;
         
         startList = gl.glGenLists(4);
         qobj = glu.gluNewQuadric();
         qobj2 = glu.gluNewQuadric();
         
         glu.gluQuadricDrawStyle(qobj, GLU.GLU_FILL);
         glu.gluQuadricNormals(qobj, GLU.GLU_SMOOTH);
         
         glu.gluQuadricDrawStyle(qobj2, GLU.GLU_FILL);
         glu.gluQuadricNormals(qobj2, GLU.GLU_SMOOTH);
         
         gl.glNewList(startList, GL.GL_COMPILE);
         
         gl.glRotatef(-scena.kat+180, 0.0f, 1.0f, 0.0f);
         gl.glColor3f(1,1,1);

        gl.glPushMatrix();
        gl.glTranslatef(0,6,0);
        gl.glRotated(90, 1, 0, 0);
        /*gl.glRotated(glowa, 0,0,0);*/
        Walec(gl, 1.0f, 1.5f, -1.5f, -1.0f, -1.25f); //glowa
        /*glu.gluSphere(qobj, 1, 20, 20);*/        

        gl.glPopMatrix();
        gl.glPushMatrix();
        
        gl.glPushMatrix();
        gl.glTranslatef(0,5,0);
        gl.glRotated(90, 1, 0, 0);
        Walec(gl, 0.5f, 1.5f, -1.5f, -1.0f, -1.25f); //szyja
        
        gl.glPopMatrix();
        gl.glPushMatrix();

        gl.glTranslatef(0,4,0);
        gl.glColor3f(1,0,0);
        gl.glRotated(90, 1, 0, 0);
        Walec(gl, 1.5f, 3.5f, -1.5f, -1.0f, -1.25f); //tu³w
        /*glu.gluCylinder(qobj2, 2, 2, 2.0, 15, 5);*/      
        
        gl.glPopMatrix(); //powrot do 0,0,0
        gl.glPushMatrix(); //zapis stanu 0,0,0
        
        gl.glTranslatef(-2,4,0);
        gl.glColor3f(1,0,1);
        gl.glRotated(90, 1, 0, 0);
        gl.glRotated(lewareka,1,0,0);
        Walec(gl, 0.5f, 5.0f, -1.5f, -1.0f, -1.25f);
        //glu.gluCylinder(qobj2, 0.5, 0.5, 5f, 15, 5);

        gl.glPopMatrix();
        gl.glPushMatrix();

        gl.glTranslatef(2,4,0);
        gl.glColor3f(1,0,1);
        gl.glRotated(90, 1, 0, 0);
        gl.glRotated(prawareka,1,0,0);
        Walec(gl, 0.5f, 5.0f, -1.5f, -1.0f, -1.25f);
        //glu.gluCylinder(qobj2, 0.5, 0.5, 5f, 15, 5);

        gl.glPopMatrix();
        gl.glPushMatrix();

        gl.glTranslatef(-1,1,0);
        gl.glColor3f(0,0,1);
        gl.glRotated(90, 1, 0, 0);
        gl.glRotated(lewanoga, 1,0,0);
        Walec(gl, 0.9f, 5.0f, -1.5f, -1.0f, -1.25f);
        //glu.gluCylinder(qobj2, 1, 1, 7f, 15, 5);

        gl.glPopMatrix();
        gl.glTranslatef(1,1,0);
        gl.glColor3f(0,0,1);
        gl.glRotated(90, 1, 0, 0);
        gl.glRotated(prawanoga, 1,0,0);
        Walec(gl, 0.9f, 5.0f, -1.5f, -1.0f, -1.25f);
        //glu.gluCylinder(qobj2, 1, 1, 7f, 15, 5);
        
        gl.glEndList();
}
    
    public void choinka(GL gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -4.0f, -4.0f);
        gl.glRotatef(-90.0f, -1.0f, 0.0f, 0.0f);
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
        walec2(gl);
        gl.glTranslatef(0.0f, 0.0f, -1.0f);
        stozek(gl);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        gl.glTranslatef(0.0f, 0.0f, -2.5f);
        stozek(gl);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        gl.glTranslatef(0.0f, 0.0f, -2.5f);
        stozek(gl);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        gl.glTranslatef(0.0f, 5.0f, 0.0f);

        gl.glPopMatrix();
    }
    


   
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"

        gl.glLoadIdentity();
        glu.gluLookAt(0,0,0,cx,cy,-1,0,1,0);
        
        gl.glTranslatef(0.0f, 0.0f, -4.0f); 
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(scena.kat, 0.0f, 1.0f, 0.0f);
        
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        
        choinka(gl);
        // Drawing Using Triangles
        gl.glTranslatef(-scena.x, 90.5f, -scena.z); 
        scena.Rysuj(gl, t1, t2, t3);
        gl.glTranslatef(scena.x, -95.5f, scena.z); 
        ludzik(gl);
               
        gl.glCallList(startList);      
       
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
