# Reading the Federalist Papers
#
# A Fed object contains one Federalist paper
# (Right now it has only the number)
#
class Fed
    attr_accessor :fedNum
    attr_accessor :fedTitle
    attr_accessor :fedPub
    attr_accessor :fedAuthor
    # Constructor
    def initialize
      @fedNum=0
      @fedTitle=""
      @fedPub=""
      @fedAuthor=""
    end

    # Method to print data on one Fed object
    def prt
       puts "Federalist #@fedNum"
       puts "Title: #{@fedTitle}"
       puts "Pub: #{@fedPub}"
       puts "Author: #{@fedAuthor}"
       puts "\n\n\n"
    end
end

#=========================
# Main program
#=========================

# Input will come from file fed.txt
file = File.new("fed.txt", "r")

# List of Fed objects
feds = []
curFed = nil
readstate = 'b4Fed'
# Read and process each line
while (line = file.gets)
    line.strip!            # Remove trailing white space
    words = line.split     # Split into array of words

    #if words.length == 0 then
      #next
    #end

    if curFed.nil?
      # "FEDERALIST No. number" starts a new Fed object
      if (readstate=='b4Fed' && words[0]=="FEDERALIST") then
         curFed = Fed.new    # Construct new Fed object
         feds << curFed      # Add it to the array
         curFed.fedNum = words[2]
         readstate = 'b4TitlePub'
         next
      end
    end

    if !curFed.nil?

      if(readstate=='b4TitlePub' && !words.empty?)
        curFed.fedTitle = words.join(' ')
        readstate = 'inTitlePub'

        next
      end

      if(readstate=='inTitlePub' && !words.empty?)
        curFed.fedTitle = curFed.fedTitle + "\n" + words.join(' ')
        #readstate = 'b4Fed'
        #curFed = nil
        next
      end

      if(readstate=='inTitlePub' && words.empty?)
        readstate = 'b4Author'
        wordsEmpty = false;
        next
      end

      if(readstate=='b4Author' && !words.empty?)
        curFed.fedAuthor = words.join(' ')
        readstate = 'b4Fed'
        curFed = nil
        next
      end
    end

end # End of reading

file.close


# Apply the prt (print) method to each Fed object in the feds array
feds.each{|f| f.prt}
#puts"#{readstate}"
