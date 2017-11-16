package com.sencha.gxt.examples.resources.server;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletContextEvent;

import com.sencha.gxt.examples.resources.server.data.Folder;
import com.sencha.gxt.examples.resources.server.data.Music;

public class MusicDataLoader {

  public static void initMusic(ServletContextEvent event) {
    EntityManager em = EMF.get().createEntityManager();
    EntityTransaction tx = em.getTransaction();

    tx.begin();
    em.persist(makeFolder());
    tx.commit();

  }

  /**
   * package protected for testing
   */
  static Folder makeFolder() {
    Folder root = makeFolder("Root");

    Folder author = makeFolder("Beethoven");
    root.addSubFolder(author);

    Folder genre = makeFolder("Quartets");
    author.addSubFolder(genre);

    genre.addMusic(makeMusic("Six String Quartets", author, genre));
    genre.addMusic(makeMusic("Three String Quartets", author, genre));
    genre.addMusic(makeMusic("Grosse Fugue for String Quartets", author, genre));

    genre = makeFolder("Sonatas");
    author.addSubFolder(genre);

    genre.addMusic(makeMusic("Sonata in A Minor", author, genre));
    genre.addMusic(makeMusic("Sonata in F Major", author, genre));

    genre = makeFolder("Concertos");
    author.addSubFolder(genre);

    genre.addMusic(makeMusic("No. 1 - C", author, genre));
    genre.addMusic(makeMusic("No. 2 - B-Flat Major", author, genre));
    genre.addMusic(makeMusic("No. 3 - C Minor", author, genre));
    genre.addMusic(makeMusic("No. 4 - G Major", author, genre));
    genre.addMusic(makeMusic("No. 5 - E-Flat Major", author, genre));

    genre = makeFolder("Symphonies");
    author.addSubFolder(genre);

    genre.addMusic(makeMusic("No. 1 - C Major", author, genre));
    genre.addMusic(makeMusic("No. 2 - D Major", author, genre));
    genre.addMusic(makeMusic("No. 3 - E-Flat Major", author, genre));
    genre.addMusic(makeMusic("No. 4 - B-Flat Major", author, genre));
    genre.addMusic(makeMusic("No. 5 - C Minor", author, genre));
    genre.addMusic(makeMusic("No. 6 - F Major", author, genre));
    genre.addMusic(makeMusic("No. 7 - A Major", author, genre));
    genre.addMusic(makeMusic("No. 8 - F Major", author, genre));
    genre.addMusic(makeMusic("No. 9 - D Minor", author, genre));

    author = makeFolder("Brahms");
    root.addSubFolder(author);

    genre = makeFolder("Concertos");
    author.addSubFolder(genre);

    genre.addMusic(makeMusic("Violin Concerto", author, genre));
    genre.addMusic(makeMusic("Double Concerto - A Minor", author, genre));
    genre.addMusic(makeMusic("Piano Concerto No. 1 - D Minor", author, genre));
    genre.addMusic(makeMusic("Piano Concerto No. 2 - B-Flat Major", author, genre));

    genre = makeFolder("Quartets");
    author.addSubFolder(genre);

    genre.addMusic(makeMusic("Piano Quartet No. 1 - G Minor", author, genre));
    genre.addMusic(makeMusic("Piano Quartet No. 2 - A Major", author, genre));
    genre.addMusic(makeMusic("Piano Quartet No. 3 - C Minor", author, genre));
    genre.addMusic(makeMusic("String Quartet No. 3 - B-Flat Minor", author, genre));

    genre = makeFolder("Sonatas");
    author.addSubFolder(genre);

    genre.addMusic(makeMusic("Two Sonatas for Clarinet - F Minor", author, genre));
    genre.addMusic(makeMusic("Two Sonatas for Clarinet - E-Flat Major", author, genre));

    genre = makeFolder("Symphonies");
    author.addSubFolder(genre);

    genre.addMusic(makeMusic("No. 1 - C Minor", author, genre));
    genre.addMusic(makeMusic("No. 2 - D Minor", author, genre));
    genre.addMusic(makeMusic("No. 3 - F Major", author, genre));
    genre.addMusic(makeMusic("No. 4 - E Minor", author, genre));

    author = makeFolder("Mozart");
    root.addSubFolder(author);

    genre = makeFolder("Concertos");
    author.addSubFolder(genre);

    genre.addMusic(makeMusic("Piano Concerto No. 12", author, genre));
    genre.addMusic(makeMusic("Piano Concerto No. 17", author, genre));
    genre.addMusic(makeMusic("Clarinet Concerto", author, genre));
    genre.addMusic(makeMusic("Violin Concerto No. 5", author, genre));
    genre.addMusic(makeMusic("Violin Concerto No. 4", author, genre));

    return root;
  }

  private static Folder makeFolder(String name) {
    Folder folder = new Folder();
    folder.setName(name);
    return folder;
  }

  private static Music makeMusic(String name, Folder author, Folder genre) {
    return makeMusic(name, author.getName(), genre.getName());
  }

  private static Music makeMusic(String name, String author, String genre) {
    Music m = new Music();
    m.setName(name);
    m.setAuthor(author);
    m.setGenre(genre);
    return m;
  }
}
