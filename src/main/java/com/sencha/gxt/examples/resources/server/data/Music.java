package com.sencha.gxt.examples.resources.server.data;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Size;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.sencha.gxt.examples.resources.server.EntityManagerUtil;

@Entity
public class Music {

  public static final EntityManager entityManager() {
    return EntityManagerUtil.getEntityManager();
  }

  public static Music findMusic(Integer id) {
    if (id == null) {
      return null;
    }

    // see if the music instance has been modified this session and return it
    Music modifiedInstance = findMusicInSession(id);
    if (modifiedInstance != null) {
      return modifiedInstance;
    }

    // music wasn't modified, return original from the database
    EntityManager em = entityManager();
    return em.find(Music.class, id);
  }

  static Music findMusicInSession(Integer id) {
    HttpServletRequest request = RequestFactoryServlet.getThreadLocalRequest();
    if (request != null) {
      HttpSession session = RequestFactoryServlet.getThreadLocalRequest().getSession();
      String key = getSessionId(id);
      Object value = session.getAttribute(key);
      if (value != null) {
        return (Music) value;
      }
    }
    return null;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Integer id;

  @Version
  private Integer version;

  @Size(min = 1, max = 100)
  private String name;

  @Size(min = 1,max = 100)
  private String author;

  @Size(min = 1,max = 100)
  private String genre;


  public String getAuthor() {
    return author;
  }

  public String getGenre() {
    return genre;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getVersion() {
    return version;
  }

  public Music persist() {
    // persist modifications to the users session only (EXTGWT-4713)
    HttpSession session = getHttpSession();
    String key = getSessionId(id);
    session.setAttribute(key, this);
    return this;
  }

  public void remove() {
    EntityManager em = entityManager();
    em.remove(this);

  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  HttpSession getHttpSession() {
    return RequestFactoryServlet.getThreadLocalRequest().getSession();
  }

  private static String getSessionId(Integer id) {
    // prefix music in case other objects get stored in session
    return "music" + id;
  }
}
