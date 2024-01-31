import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.info.mybook.R
import com.info.mybook.models.User
import com.info.mybook.databinding.ItemUserBinding
import com.info.mybook.models.Users


class UserAdapter : ListAdapter<Users, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val alluser = getItem(position)
        holder.bind(alluser)
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: Users) {
            binding.user = users
            Glide.with(binding.profileimage)
                .load(users.image)
                .placeholder(R.drawable.circularbordersolid)
                .error(R.drawable.circularbordersolid)
                .into(binding.profileimage)
            binding.executePendingBindings()
//            itemView.setOnClickListener {
//                onItemClickedListener.onItemClick(user)
//            }
        }
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<Users>() {
        override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem == newItem
        }
    }
}