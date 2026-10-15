package com.example.mad_assignment_zmb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_assignment_zmb.models.YardModel

class YardAdapter (
    private var yards: List<YardModel>,
    private val listener: OnYardListener
    ) : RecyclerView.Adapter<YardAdapter.YardViewHolder>() {

        class YardViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

            val titleText: TextView =
                itemView.findViewById(R.id.titleText)

            val descriptionText: TextView =
                itemView.findViewById(R.id.descriptionText)

            val priceText: TextView =
                itemView.findViewById(R.id.priceText)

            val websiteLink: TextView =
                itemView.findViewById(R.id.websiteLinkText)

            val phone_numText: TextView =
                itemView.findViewById(R.id.phone_numText)

            val ageLimitText: TextView =
                itemView.findViewById(R.id.ageLimitText)

        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): YardViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.yard_item,
                    parent,
                    false
                )

            return YardViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: YardViewHolder,
            position: Int
        ) {

            val yard = yards[position]

            holder.titleText.text =
                "${yard.id}: ${yard.title}"

            holder.descriptionText.text =
                "Description: ${yard.desc}"

            holder.priceText.text =
                "Price €${yard.price}"

            holder.websiteLink.text =
                "Website URL: ${yard.websitelink}"

            holder.phone_numText.text =
                "Phone Number: ${yard.phone_num}"

            holder.ageLimitText.text =
                "Age Limit for Horse-Riding Lessons: ${yard.age_limit}"

            holder.itemView.setOnClickListener {

                listener.onYardClick(yard)

            }
            holder.itemView.setOnLongClickListener {

                listener.onYardLongClick(yard)

            }

        }

        override fun getItemCount(): Int {
            return yards.size
        }

        fun updateYards(newYard: List<YardModel>) {
            yards = newYard
            notifyDataSetChanged()
        }
}
