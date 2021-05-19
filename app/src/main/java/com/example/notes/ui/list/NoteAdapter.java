package com.example.notes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int NOTE = 0;
    private final static int HEADER = 1;

    private NoteAdapterClickListener listener;
    private List<AdapterItem> data = new ArrayList<>();
    private int longClickPosition = -1;
    private final FragmentList fragmentList;

    public NoteAdapter(FragmentList fragmentList) {
        this.fragmentList = fragmentList;
    }

    public void setListener(NoteAdapterClickListener listener) {
        this.listener = listener;
    }

    public int getLongClickPosition() {
        return longClickPosition;
    }

    public interface NoteAdapterClickListener {
        void noteAdapterClickListener(Note note);
    }

    public Note getNoteByPosition(int position) {
        if (data.size() > position) return (Note) (data.get(position)).getItem();
        return new Note("empty", "empty", "empty");
    }

    public void setData(List<AdapterItem> toAdd) {
        NotesDiffutilCallback callback = new NotesDiffutilCallback(this.data, toAdd);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        this.data.clear();
        this.data.addAll(toAdd);

        result.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof HeaderItem) {
            return HEADER;
        }
        if (data.get(position) instanceof NoteItem) {
            return NOTE;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == HEADER) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item, parent, false);
            return new HeaderViewHolder(view);
        }
        if (viewType == NOTE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
            return new NotesViewHolder(view);
        }
        throw new IllegalStateException("Can't do it");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AdapterItem item = data.get(position);
        if (holder instanceof NotesViewHolder) {
            NotesViewHolder notesViewHolder = (NotesViewHolder) holder;
            Note note = (Note) item.getItem();
            notesViewHolder.bind(note);
        }
        if (holder instanceof HeaderViewHolder) {
            String title = (String) item.getItem();
            ((HeaderViewHolder) holder).textView.setText(title);
        }

    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title_tv);
        }
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        TextView date;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            content = itemView.findViewById(R.id.item_content);
            date = itemView.findViewById(R.id.item_date);
            fragmentList.registerForContextMenu(itemView);
            itemView.setOnLongClickListener(v -> {
                longClickPosition = getAdapterPosition();
                itemView.showContextMenu();
                return true;
            });
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.noteAdapterClickListener((Note) (data.get(getAdapterPosition())).getItem());
                }
            });
        }

        public void bind(Note note) {
            title.setText(note.getTitle());
            content.setText(note.getContent());
            date.setText(note.getCurrentDate());
        }

    }

    public static class NotesDiffutilCallback extends DiffUtil.Callback {

        private final List<AdapterItem> oldList;
        private final List<AdapterItem> newList;

        public NotesDiffutilCallback(List<AdapterItem> oldList, List<AdapterItem> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getUniqueTag().equals(newList.get(newItemPosition).getUniqueTag());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }

}
