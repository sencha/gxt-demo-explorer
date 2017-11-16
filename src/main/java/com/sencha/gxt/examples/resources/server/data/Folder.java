package com.sencha.gxt.examples.resources.server.data;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import com.sencha.gxt.examples.resources.server.EntityManagerUtil;

@Entity
public class Folder {

  public static final EntityManager entityManager() {
    return EntityManagerUtil.getEntityManager();
  }

  public static List<Folder> findAllFolders() {
    EntityManager em = entityManager();
    List<Folder> list = em.createQuery("select f from Folder f", Folder.class).getResultList();
    return list;
  }

  public static Folder findFolder(Integer id) {
    if (id == null) {
      return null;
    }
    EntityManager em = entityManager();
    Folder session = em.find(Folder.class, id);
    return session;
  }

  public static Folder getRootFolder() {
    EntityManager em = entityManager();
    TypedQuery<Folder> q = em.createQuery("select f from Folder f where f.parentFolder is null", Folder.class);
    return q.getSingleResult();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Version
  private Integer version;

  @Size(min = 1, max = 100)
  private String name;

  @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = {})
  private Folder parentFolder;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Folder> subFolders = new ArrayList<Folder>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Music> children = new ArrayList<Music>();

  public void addMusic(Music music) {
    children.add(music);
  }

  public void addSubFolder(Folder subFolder) {
    subFolder.parentFolder = this;
    subFolders.add(subFolder);
  }

  public List<Music> getChildren() {
    final List<Music> children = new ArrayList<Music>(this.children);
    for (ListIterator<Music> it = children.listIterator(); it.hasNext();) {
      Music music = it.next();
      // look in our session to see if the music instance has been modified from it's original
      Music sessionModified = Music.findMusicInSession(music.getId());
      if (sessionModified != null) {
        it.remove();
        it.add(sessionModified);
      }
    }
    return children;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<Folder> getSubFolders() {
    return subFolders;
  }

  public Integer getVersion() {
    return version;
  }

  public Folder persist() {
    EntityManager em = entityManager();
    return em.merge(this);
  }

  public void remove() {
    EntityManager em = entityManager();
    Folder attached = em.find(Folder.class, this.id);
    em.remove(attached);
  }

  public void setChildren(List<Music> children) {
    this.children = children;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSubFolders(List<Folder> subFolders) {
    this.subFolders = subFolders;
  }

  public Folder getParentFolder() {
    return parentFolder;
  }

}
