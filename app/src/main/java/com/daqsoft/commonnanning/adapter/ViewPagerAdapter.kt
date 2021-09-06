package com.daqsoft.commonnanning.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * ViewPager和TabLayout联动的Adapter
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-23
 * @since JDK 1.8.0_191
 */
class ViewPagerAdapter(private val fm: FragmentManager?,
                       private val fragments: ArrayList<Fragment>,
                       private val titles: ArrayList<String>) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}