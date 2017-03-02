import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * GC.java
 *
 * This question is one of those if you could understand the problem
 * type of questions. These types of references (Strong, soft, weak,
 * and phantom) are used so that we as the programmers can interact
 * with the GC.
 *
 * Notice that only the strong and soft references matter for this
 * simulation, because weak and phantom references don't prevent
 * garbage collection, and is equivalent to no connection at all.
 *
 * Since the numbers are small enough, we can do a simple modified
 * bfs (breath-first-search) to solve this problem, to find which
 * objects are no longer accessable. We only need to run this check 
 * during the UNREF instruction and the CREATE instruction (if we
 * run out of memory). 
 *
 * We use two classes to help sectionalize certain data stuff.
 * The Instruction class holds one instruction. For simplicity
 * of access, all fields of the class are public, so we can
 * directly access it. The Obj class holds data for objects
 * created.
 *
 * @author Henry
 *
 */
public class GC
{
	public static class Instruction {

		public static Instruction parseInstruction(String line)
		{
			String[] ops = line.split(" ");
			if (ops[0].equals("CREATE"))
				return new Instruction(CREATE, 0,
						Integer.parseInt(ops[1]), -1, Integer.parseInt(ops[2]));
			else if (ops[0].equals("REF"))
			{
				int refType = 0;
				if (ops[1].equals("STRONG"))
					refType = REF_STRONG;
				else if (ops[1].equals("SOFT"))
					refType = REF_SOFT;
				return new Instruction(REF, refType,
						Integer.parseInt(ops[2]), Integer.parseInt(ops[3]), -1);
			}
			else if (ops[0].equals("UNREF"))
				return new Instruction(UNREF, 0,
						Integer.parseInt(ops[1]), Integer.parseInt(ops[2]), -1);
			else
				throw new RuntimeException("Illegal instruction " + ops[0]);
		}



		public int opcode; //CREATE, REF, or UNREF
		public int type; //REF_STRONG, REF_SOFT
		public int xAddr;
		public int yAddr; //for REF or UNREF
		public long size; //for CREATE

		private Instruction(int opcode, int type, int xAddr, int yAddr, long size)
		{
			this.opcode = opcode;
			if (xAddr != -1)
				xAddr--; //make address zero-indexed
			this.xAddr = xAddr;
			if (yAddr != -1)
				yAddr--; //make address zero-indexed
			this.yAddr = yAddr;
			this.size = size;
			this.type = type;
		}
	}

	public static class Obj implements Comparable<Obj>{
		public long size;
		public int address;

		//References to other objects
		public HashSet<Obj> refs = new HashSet<>();
		public HashSet<Obj> refsSoft = new HashSet<>();

		public Obj(int address, long size)
		{
			this.size = size;
			this.address = address;
		}

		@Override
		public int compareTo(Obj o)
		{
			return address - o.address;
		}

		@Override
		public boolean equals(Object other)
		{
			return this == other;
		}

		@Override
		public int hashCode()
		{
			return address;
		}

		public void ref(Obj other, boolean soft)
		{
			if (refs.contains(other))
				return;
			if (soft)
			{
				refsSoft.add(other);
				other.refsSoft.add(this);
			}
			else
			{
				refs.add(other);
				other.refs.add(this);
				refsSoft.remove(other);
				other.refsSoft.remove(this);
			}
		}
		
		public void unref(Obj other)
		{
			refs.remove(other);
			other.refs.remove(this);
			refsSoft.remove(other);
			other.refsSoft.remove(this);
		}
		
		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder();
			sb.append(address).append(":");
			for (Obj o : refs)
				sb.append(" ").append(o.address);
			if (!refsSoft.isEmpty())
			{
				sb.append(" ***");
				for (Obj o : refsSoft)
					sb.append(" ").append(o.address);
			}
			return sb.toString();
		}
	}

	public static final int CREATE = 0;
	public static final int REF = 1;
	public static final int UNREF = 2;

	public static final int REF_STRONG = 2;
	public static final int REF_SOFT = 1;

	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("gc.in"));
		System.setOut(new PrintStream(new File("gc.out"))); //Comment this line to print output to console

		int t = in.nextInt();
		CASE: for (int cases = 0; cases < t; cases++)
		{
			int frames = in.nextInt();
			long maxHeap = in.nextInt();
			long heapSize = 0;
			in.nextLine();

			//Read instructions
			int maxAddr = 0;
			ArrayList<Instruction> instructions = new ArrayList<>(frames);
			for (int i = 0; i < frames; i++)
			{
				Instruction line = Instruction.parseInstruction(in.nextLine());
				if (line.opcode == CREATE)
					maxAddr = Math.max(maxAddr, line.xAddr);
				instructions.add(line);
			}

			Obj root = new Obj(-1, 0);
			Obj[] heap = new Obj[maxAddr + 1];
			HashSet<Obj> objList = new HashSet<>();
			
			int num = 1;
			for (Instruction instruct : instructions)
			{
				int x = instruct.xAddr;
				int y = instruct.yAddr;

				switch (instruct.opcode)
				{
				case CREATE:
					heapSize += instruct.size;
					if (heapSize > maxHeap) //TODO: case where we have soft references and curHeap = maxHeap
					{
						if (heapSize != 0)
							heapSize -= gc(num, heap, objList, root, false);
						if (heapSize > maxHeap)
						{
							printFrame(num, "OUTOFMEMORYERROR");
							continue CASE;
						}
					}
					
					Obj created = new Obj(x, instruct.size);
					root.ref(created, false);
					heap[x] = created;
					objList.add(created);
					break;
				case REF:
					if (instruct.type == 0)
						break;
					if (x == -1)
						root.ref(heap[y], instruct.type == REF_SOFT);
					else
						heap[x].ref(heap[y], instruct.type == REF_SOFT);
					break;
				case UNREF:
					if (x == -1)
						root.unref(heap[y]);
					else
						heap[x].unref(heap[y]);
					heapSize -= gc(num, heap, objList, root, true);
				}
				num++;
			}
		}

	}

	public static int gc(int frame, Obj[] heap, HashSet<Obj> objList, Obj root, boolean includeSoft)
	{
		HashSet<Obj> gone = new HashSet<>(objList);
		boolean[] visited = new boolean[heap.length];
		Queue<Obj> objs = new ArrayDeque<>();

		objs.addAll(root.refs);
		while (!objs.isEmpty())
		{
			Obj o = objs.poll();
			if (o == root)
				continue;
			if (visited[o.address])
				continue;
			
			gone.remove(o);
			visited[o.address] = true;
			
			//System.out.println(o);
			
			if (includeSoft)
				objs.addAll(o.refsSoft);
			objs.addAll(o.refs);
		}
		//System.out.println(gone.size());
		
		//Remove soft references.
		if (!includeSoft)
		{
			for (Obj o : gone)
				for (Obj oo : o.refsSoft)
					oo.refsSoft.remove(o);
		}
		
		//Count mem and remove unreachable objects.
		int mem = 0;
		for (Obj o : gone)
		{
			mem += o.size;
			heap[o.address] = null;
		}
		objList.removeAll(gone);
		
		if (!gone.isEmpty())
		{
			
			TreeSet<Obj> removed = new TreeSet<>(gone);
			printFrame(frame, removed);
		}
		
		return mem;
	}

	public static void printFrame(int frame, TreeSet<Obj> removed)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(frame).append(":");

		for (Obj o : removed)
			sb.append(" ").append(o.address + 1);
		System.out.println(sb.toString());
	}
	public static void printFrame(int frame, String text)
	{
		System.out.println(frame + ": " + text);
	}

}