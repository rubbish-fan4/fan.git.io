package com.xin.service;

import com.xin.dao.BlogsRepository;
import com.xin.pojo.Blog;
import com.xin.pojo.Type;
import com.xin.util.MarkdownUtils;
import com.xin.util.MyBeanUtils;
import com.xin.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;


@Service
public class BlogsServiceImpl implements BlogsService {



    @Autowired
    private BlogsRepository blogsRepository;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogsRepository.getOne(id);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogsRepository.getBlogById(id);
        if (blog == null){
            return null;   //要抛出异常
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = blog.getContent();
        blog.setContent( MarkdownUtils.markdownToHtmlExtensions(content));
        //添加浏览次数
        blogsRepository.updateViews(id);
        return b;
    }

    @Transactional
    @Override
    public Blog getBlogById(Long id) {
        return blogsRepository.getBlogById(id);
    }

    @Transactional
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogsRepository.findTop(pageable);
    }

    /**
     * 获得每个年份的博客
     * @return 博客
     */
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogsRepository.findGroupYear();   //获取所有的年份
        Map<String,List<Blog>> map = new LinkedHashMap<>();
        for (String year : years){
            map.put(year,blogsRepository.findByYear(year));
        }
        return map;
    }

    /**
     * 获取博客的数量
     * @return 返回数据
     */
    @Override
    public Long countBlog() {
        return blogsRepository.count();
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogsRepository.findAll(new Specification<Blog>() {
            /**
             * 组合查询
             * @param root 你要查询的对象，可以拿到一些属性值
             * @param cq 查询的条件容器，
             * @param cd 设置具体条件的表达式
             */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cd) {
                //这一段没看明白，但是就是根据，几个条件查询数据
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle()!=null){
                    predicates.add(cd.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null){
                    predicates.add(cd.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if (blog.isRecommend()){
                    predicates.add(cd.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogsRepository.findByQuery(query,pageable);
    }


    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogsRepository.findAll(pageable);
    }

    /**
     * 通过标签的id查询博客进行分页，blog表与tag表的关联
     * @param tagId 标签的id
     * @param pageable 分页处理
     * @return  返回分类号的
     */
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogsRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //把绑定
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null){  //新增
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogsRepository.save(blog);
    }

    @Transactional
    @Query
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogsRepository.getBlogById(id);
        if (b == null){
            return null;   //需要修改成自定义异常
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogsRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogsRepository.deleteById(id);
    }
}
